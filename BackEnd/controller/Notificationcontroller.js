const Notification = require("../model/Notificationmodel");

const createNotification = async (req, res) => {
  const { title, message } = req.body;

  if (!title || !message) {
    return res.status(400).json({ error: "Title and message are required." });
  }

  try {
    const newNotification = new Notification({
      title,
      message,
      createdAt: new Date().toISOString(),
    });

    const savedNotification = await newNotification.save();
    res.status(201).json(savedNotification);
  } catch (err) {
    console.error(err);
    res.status(500).json({ error: "Server error while creating notification" });
  }
};

const getallNotification = async (req, res) => {
  try {
    const notifications = await Notification.find().sort({ createdAt: -1 });
    res.status(200).json(notifications);
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
};

module.exports = {
  createNotification,
  getallNotification,
};
