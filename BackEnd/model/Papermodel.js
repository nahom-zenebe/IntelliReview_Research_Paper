const mongoose = require("mongoose");

const paperSchema = new mongoose.Schema({
  title: { type: String, required: true },
  authors: { type: [String], required: true },
  year: { type: Number, required: true },
  pdfUrl: { type: String, required: true },
  uploadedBy: {
    type: mongoose.Schema.Types.ObjectId,
    ref: "User",
    required: true,
  },
  catagory: {
    type: mongoose.Schema.Types.ObjectId,
    require: true,
  },
  averageRating: { type: Number, default: 0, required: true },
  reviewCount: { type: Number, default: 0, required: true },
  createdAt: { type: Date, default: Date.now, required: true },
  updatedAt: { type: Date, default: Date.now, required: true },
});

module.exports = mongoose.model("Paper", paperSchema);
