const reviewSchema = new mongoose.NumberSchema({
  paperId: {
    type: mongoose.Schema.Types.ObjectId,
    ref: "Paper",
    required: true,
  },
  userId: { type: mongoose.Schema.Types.ObjectId, ref: "User", required: true },
  rating: { type: Number, min: 1, max: 5, required: true },

  comment: { type: String },
  createdAt: { type: Date, default: Date.now },
  updatedAt: { type: Date, default: Date.now },
});

module.exports = reviewSchema;
