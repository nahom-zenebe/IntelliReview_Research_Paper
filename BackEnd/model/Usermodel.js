
const mongoose=require("mongoose")

const UserSchema= new mongoose.Schema({

    name:{
        type:String,
        require:true
    },
    email: {
      type: String,
      require: true,
      unique: true,
    },
    password: {
      type: String,
      require: true,
    },
    country:{
      type:String,
      required:true
    },

    role: {
      type: String,
      enum: ["admin", "user", "guest"],
      default: "user",
    },
    ProfilePic: {
      type: String,
    },
    createdAt: {
      type: Date,
      default: Date.now,
    },
  },
  { timestamps: true }
);

const User = mongoose.model("User", UserSchema);

module.exports = User;
