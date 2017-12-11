# Spark-MachineLearning
In the previous assignment, you created a program for producing labeled data consisting of an input
vector of 21x21x7 brightness values from an image, together with the center pixel’s foreground-vsbackground
label. Use this program to generate as many labeled records as you like, making sure the
entire training data set is so big that does not fit into the memory of a single machine. You decide how
you select the center pixels and how many foreground and background pixels you want to include. You
are also free to choose if you want to include rotated and mirrored versions of the labeled records. For
those who did not complete the last assignment or just want another option for testing, we will also
provide labeled data. (Announcement will be made soon.)
Use the labeled data to train the most accurate model for predicting if a pixel in a new image file is
foreground or background. You can use the labeled data in any way you like for model training,
parameter tuning, and testing. For final evaluation, the labels predicted for all records in the unlabeled
data set (released soon) have to be submitted. There the labels are all replaced by “?”
