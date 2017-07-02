if (window.WebSocket) {
    stompConnect()
}

function stompConnect() {
    const wsUrl = `ws://${window.location.host}${contextRoot}/ws`
    console.log('wsUrl: ', wsUrl)

    const client = webstomp.client(wsUrl)

    client.connect({}, () => {
        document.getElementById('ws-status').innerText = 'Online'
        client.subscribe('/topic/status', onStatusMessageReceived)
    }, onStompError)
}

function onStatusMessageReceived(message) {
    const data = JSON.parse(message.body)
    console.log("received: ", message, message.body, data)
    // TODO view framework
    document.getElementById('client-version').innerText = data.clientVersion
    document.getElementById('protocol-version').innerText = data.protocolVersion
    document.getElementById('listening').innerText = data.listening
    document.getElementById('peer-count').innerText = data.peerCount
    document.getElementById('syncing').innerText = data.syncing
    document.getElementById('block-number').innerText = data.blockNumber
    document.getElementById('gas-price').innerText = data.gasPrice.toLocaleString()
    const now = new Date()
    document.getElementById('last-updated').innerText = moment().format('DD MMM YYYY HH:mm:ss')
}

function onStompError(error) {
    document.getElementById('ws-status').innerText = 'Offline'
    console.log('STOMP: ' + error);
    setTimeout(stompConnect, 10000);
    console.log('STOMP: Reconnecting in 10 seconds...');
}
