import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) throws Exception {

        HttpServer server = HttpServer.create(
            new InetSocketAddress(8080), 0
        );

        server.createContext("/", exchange -> {

            String html = """
                <!DOCTYPE html>
                <html>
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport"
                          content="width=device-width, initial-scale=1.0">
                    <title>My Java Docker App</title>

                    <style>
                        * {
                            box-sizing: border-box;
                        }

                        body {
                            margin: 0;
                            min-height: 100vh;
                            font-family: Arial, sans-serif;
                            background: linear-gradient(
                                135deg, #667eea, #764ba2, #f093fb
                            );
                            display: flex;
                            justify-content: center;
                            align-items: center;
                            text-align: center;
                            padding: 20px;
                            transition: background 0.5s;
                        }

                        .card {
                            background: white;
                            max-width: 750px;
                            width: 100%;
                            padding: 45px 25px;
                            border-radius: 25px;
                            box-shadow: 0 15px 40px
                                rgba(0,0,0,0.25);
                        }

                        h1 {
                            color: #5135a5;
                            font-size: clamp(32px, 6vw, 48px);
                            margin-bottom: 15px;
                        }

                        .message {
                            color: #333;
                            font-size: clamp(20px, 4vw, 27px);
                            font-weight: bold;
                            line-height: 1.5;
                        }

                        .subtitle {
                            color: #666;
                            font-size: 18px;
                        }

                        .tags {
                            margin: 25px 0;
                        }

                        .tag {
                            display: inline-block;
                            background: #eee7ff;
                            color: #5135a5;
                            padding: 10px 18px;
                            border-radius: 25px;
                            margin: 5px;
                            font-weight: bold;
                        }

                        button {
                            border: none;
                            padding: 14px 22px;
                            margin: 7px;
                            border-radius: 12px;
                            font-size: 16px;
                            font-weight: bold;
                            cursor: pointer;
                            color: white;
                            background: #6841c6;
                            transition: 0.3s;
                        }

                        button:hover {
                            transform: translateY(-3px);
                            opacity: 0.85;
                        }

                        .orange {
                            background: #ed9135;
                        }

                        .dark {
                            background: #252044;
                        }

                        #result {
                            margin-top: 20px;
                            color: #5135a5;
                            font-size: 21px;
                            font-weight: bold;
                        }
                    </style>
                </head>

                <body>
                    <div class="card">

                        <h1>Hello, Docker World! 🚀</h1>

                        <p class="message">
                            Hello! My Java application
                            is running inside Docker!
                        </p>

                        <p class="subtitle">
                            Built with Java 17 and powered by Docker.
                        </p>

                        <div class="tags">
                            <span class="tag">Java 17</span>
                            <span class="tag">Docker</span>
                            <span class="tag">Container</span>
                        </div>

                        <button onclick="sayHello()">
                            Say Hello 👋
                        </button>

                        <button class="orange"
                                onclick="changeBackground()">
                            Change Background 🎨
                        </button>

                        <button class="dark"
                                onclick="resetPage()">
                            Reset Page ↻
                        </button>

                        <div id="result">
                            Your application is live! 🎉
                        </div>

                    </div>

                    <script>
                        const backgrounds = [
                            "linear-gradient(135deg,#667eea,#764ba2,#f093fb)",
                            "linear-gradient(135deg,#11998e,#38ef7d)",
                            "linear-gradient(135deg,#fc4a1a,#f7b733)",
                            "linear-gradient(135deg,#2193b0,#6dd5ed)"
                        ];

                        let current = 0;

                        function sayHello() {
                            document.getElementById("result")
                                .innerText =
                                "Welcome! Your Docker application is working! 🎉";
                        }

                        function changeBackground() {
                            current = (current + 1) %
                                backgrounds.length;

                            document.body.style.background =
                                backgrounds[current];
                        }

                        function resetPage() {
                            current = 0;

                            document.body.style.background =
                                backgrounds[0];

                            document.getElementById("result")
                                .innerText =
                                "Your application is live! 🎉";
                        }
                    </script>
                </body>
                </html>
                """;

            byte[] response =
                html.getBytes(StandardCharsets.UTF_8);

            exchange.getResponseHeaders().set(
                "Content-Type", "text/html; charset=UTF-8"
            );

            exchange.sendResponseHeaders(200, response.length);

            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response);
            }
        });

        server.start();

        System.out.println(
            "Java application running on port 8080"
        );
    }
}
