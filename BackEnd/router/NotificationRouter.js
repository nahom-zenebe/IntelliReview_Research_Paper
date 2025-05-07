const express = require("express");
const router = express.Router();
const {
  notifyNewPaper,
  notifyInactiveUsers,
  getUserNotifications,
} = require("../controller/Notificationcontroller");

router.post("/notify-new-paper", notifyNewPaper);
router.post("/notify-inactive-users", notifyInactiveUsers);
router.get("/:userId", getUserNotifications);

module.exports = router;
