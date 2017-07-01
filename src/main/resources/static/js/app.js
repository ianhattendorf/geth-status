if (window.WebSocket) {
    const wsUrl = `ws://${window.location.host}${contextRoot}/ws`
    console.log('wsUrl: ', wsUrl)

    const client = webstomp.client(wsUrl)

    client.connect({}, () => {
        client.subscribe('/topic/status', message => {
            const data = JSON.parse(message.body)
            console.log("received: ", message, message.body, data)
            // TODO view framework
            document.getElementById('protocol-version').innerText = data.protocolVersion
            document.getElementById('listening').innerText = data.listening
            document.getElementById('peer-count').innerText = data.peerCount
            document.getElementById('syncing').innerText = data.syncing
            document.getElementById('block-number').innerText = data.blockNumber
        })
    })
}
