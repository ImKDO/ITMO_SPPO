import com.fastcgi.FCGIInterface
import java.nio.ByteBuffer
import java.nio.charset.StandardCharsets


fun main() {
    var fcgiInterface = FCGIInterface()
    while (fcgiInterface.FCGIaccept() >= 0) {

        var content = """
                <html>
                <head><title>Java FastCGI Hello World</title></head>
                <body><hi>Hello, World!</h1></body>
                </html>";
                """

        var readRequest = readRequestBody().also {
            println(it)
        }

        var httpResponse =
            """HTTP/1.1 200 OK
                Content-Type: text/html
                Content-Length: %d
                
                %s
""".format(content.toByteArray(StandardCharsets.UTF_8).size, content)
        println(httpResponse);
    }

}

private fun readRequestBody(): String {
    FCGIInterface.request.inStream.fill()
    val contentLength = FCGIInterface.request.inStream.available()
    val buffer: ByteBuffer = ByteBuffer.allocate(contentLength)
    val readBytes =
        FCGIInterface.request.inStream.read(
            buffer.array(), 0,
            contentLength
        )
    val requestBodyRaw = ByteArray(readBytes)
    buffer.get(requestBodyRaw)
    buffer.clear()
    return String(requestBodyRaw, StandardCharsets.UTF_8)
}