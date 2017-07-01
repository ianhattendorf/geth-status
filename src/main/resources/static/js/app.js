if (window.WebSocket) {
    const wsUrl = `ws://${window.location.host}${contextRoot}/ws`
    console.log('wsUrl: ', wsUrl)

    const client = webstomp.client(wsUrl)

    client.connect({}, () => {
        client.subscribe('/topic/status', message => {
            const data = JSON.parse(message.body)
            console.log("received: ", message, message.body, data)
            document.getElementById('peer-count').innerText = data.peerCount
        })
    })
}
