require("dotenv").config();

const express = require("express");
const authrouter = require("./router/authRouther");
const paperRouter = require("./router/paperRouter");
const ReviewRouter = require("./router/ReviewRouter");
const CategoryRouter = require("./router/CategoryRouter");
const NotificationRouter = require("./router/NotificationRouter");
const { MongoDBconfig } = require("./utils/mongoconfig");
const bodyParser = require("body-parser");
const cors = require("cors");
const app = express();

app.use(express.json());
app.use("/api/auth", authrouter);
app.use("/api/paper", paperRouter);
app.use("/api/review", ReviewRouter);
app.use("/api/category", CategoryRouter);
app.use("/notification", NotificationRouter);

const PORT = process.env.PORT || 3003;

app.use(bodyParser.json()); // for parsing application/json
app.use(bodyParser.urlencoded({ extended: true })); // for parsing application/x-www-form-urlencoded

app.use(
  cors({
    origin: "*",
    methods: ["GET", "POST", "PUT", "DELETE"],
    credentials: true,
  })
);

app.listen(PORT, (err) => {
  if (err) {
    console.log(`error while listening on PORT ${process.env.PORT}`, err);
  }
  MongoDBconfig();
  console.log(`Listening on port ${process.env.PORT}...`);
});
