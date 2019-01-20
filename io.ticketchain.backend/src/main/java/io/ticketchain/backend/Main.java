package io.ticketchain.backend;

public class Main {
    public static void main(String[] args) {
        spark.Spark.get("/hello",
                (req, res) -> {
                    res.status(100);
                    res.body("Hello World");
                    return res;
                });
    }
}
