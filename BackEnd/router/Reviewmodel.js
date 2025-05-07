const express=require("express")
const router=express.Router()
const {createReview ,getAllReviews,getReviewById ,updateReview,deleteReview,calculatereview}=require('../controller/Authcontroller')







router.post("/CreateReview",createReview)
router.get("/getallReview",getAllReviews)
router.get("/getsingleReview/:ReviewId",getReviewById)
router.get("/CalculateReview",calculatereview)
router.delete("/DeleteReview/:ReviewId",deleteReview)
router.put("/updateReview/:ReviewId",updateReview)









module.exports=router