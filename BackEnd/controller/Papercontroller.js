const Paper = require("../model/Papermodel");

// Upload Paper Function
const uploadPaper = async (req, res) => {
  try {
    const { title, authors, year, uploadedBy, catagory } = req.body;

    const newPaper = new Paper({
      title,
      authors: Array.isArray(authors) ? authors : [authors],
      year,
      pdfUrl: req.file.path,
      uploadedBy,
      catagory,
    });

    const savedPaper = await newPaper.save();

    res.status(201).json({
      message: "Paper uploaded successfully",
      paper: savedPaper,
    });
  } catch (error) {
    console.error("Error uploading paper:", error.message);
    res.status(500).json({ error: "Failed to upload paper: " + error.message });
  }
};

// Update Paper Function
const updatePaper = async (req, res) => {
  try {
    const { id } = req.params;
    const { title, authors, year, catagory } = req.body;

    const updateFields = {
      title,
      year,
      catagory,
      authors: Array.isArray(authors) ? authors : [authors],
      updatedAt: Date.now(),
    };

    if (req.file) {
      updateFields.pdfUrl = req.file.path;
    }

    const updatedPaper = await Paper.findByIdAndUpdate(id, updateFields, {
      new: true,
    });

    if (!updatedPaper) {
      return res.status(404).json({ error: "Paper not found" });
    }

    res.status(200).json({
      message: "Paper updated successfully",
      paper: updatedPaper,
    });
  } catch (error) {
    console.error("Error updating paper:", error.message);
    res.status(500).json({ error: "Failed to update paper: " + error.message });
  }
};

// View Ppwer Function
const viewPaper = async (req, res) => {
  try {
    const papers = await Paper.find({});
    if (papers && papers.length > 0) {
      return res.status(200).json(papers);
    }
    return res.status(404).send("No papers found");
  } catch (error) {
    console.error("Error while fetching papers:", error.message);
    res.status(500).send("Error while fetching papers");
  }
};

// Delete Paper Function
const deletePaper = async (req, res) => {
  try {
    const { id } = req.params;

    const deleted = await Paper.findByIdAndDelete(id);

    if (!deleted) {
      return res.status(404).send("Paper not found");
    }

    res.status(200).send("Paper deleted successfully");
  } catch (error) {
    res.status(500).send("Error while deleting paper");
  }
};

module.exports = { uploadPaper, deletePaper, updatePaper, viewPaper };
