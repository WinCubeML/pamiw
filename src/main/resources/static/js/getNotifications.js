var sse = new EventSource('http://localhost:8080/notifysse');
sse.onmessage = function (e) {
    alert("Nowa publikacja o nazwie: " + e.data);
};