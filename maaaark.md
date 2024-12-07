This project implements various machine learning models to classify and
predict outcomes across six datasets using \*Python and scikit-learn.
Each script is designed to be executed in \*\*Google Colab\*. \#
1)Classification Tasks

*Requirements* - Python 3.x - Libraries: - pandas - numpy -scikit-learn
-matplotlib

/content/drive/MyDrive/Project/Classification/

TrainData/
    TrainData1.txt
    TrainData2.txt
    TrainData3.txt
    TrainData4.txt
    TrainData5.txt
    TrainData6.txt
TrainLabel/
    TrainLabel1.txt
    TrainLabel2.txt
    TrainLabel3.txt
    TrainLabel4.txt
    TrainLabel5.txt
    TrainLabel6.txt
TestData/
    TestData1.txt
    TestData2.txt
    TestData3.txt
    TestData4.txt
    TestData5.txt
    TestData6.txt



*Setup* *Upload the Datasets* : Place the training and test dataset
files (TrainDataX.txtTrainLabelX.txt, and TestDataX.txt) in the
corresponding directories in your Google Drive. *Install Dependencies* :
Run the following command in a Colab notebook to install the required

*!pip install pandas numpy scikit-learn matplotlib imblearn (all
necessary modules)*

*How to Run* 1. Open Google Colab and mount your Google Drive:

\*\*from google.colab import drive* *drive.mount('/content/drive')\*

4.  Upload the script for the dataset you want to run into a Colab cell.
5.  Execute the script to process the data, train models, and test the
    predictions. *Input files:* *train_data =
    pd.read_csv("/content/drive/MyDrive/Project/Classification/TrainData/TrainData5.txt",
    delimiter="`\t"`{=tex}, header=None)* *train_labels =
    pd.read_csv("/content/drive/MyDrive/Project/Classification/TrainLabel/TrainLabel5.txt",
    delimiter="`\t"`{=tex}, header=None)*

-   *Outputs*
    :predictions_df.to_csv("/content/drive/MyDrive/Project/Classification/model_predictions_dataset5.csv",
    index=False)

# 2) Missing value Estimation

# Input File:

**file_paths = \[
"/content/drive/MyDrive/Project/MissingData/MissingData1.txt",
"/content/drive/MyDrive/Project/MissingData/MissingData2.txt",
"/content/drive/MyDrive/Project/MissingData/MissingData3.txt"\]**

*Output Saved* :

Imputed dataset saved to:
/content/drive/MyDrive/Project/MissingData/MissingData1_imputed.csv
Imputed dataset saved to:
/content/drive/MyDrive/Project/MissingData/MissingData2_imputed.csv
Imputed dataset saved to:
/content/drive/MyDrive/Project/MissingData/MissingData3_imputed.csv

# 3) Multi-Label Classifier

File Paths Ensure the following files are available at the specified
paths:

*File Path* train_data_txt =
'/content/drive/MyDrive/Project/MultiLabelData/MultLabelTrainData.txt'
train_label_txt =
'/content/drive/MyDrive/Project/MultiLabelData/MultLabelTrainLabel.txt'
test_data_txt =
'/content/drive/MyDrive/Project/MultiLabelData/MultLabelTestData.txt'
output_file =
'/content/drive/MyDrive/Project/MultiLabelData/GangavaramMultiLableResult.txt'

3.  *Output* : *The test predictions are saved to*
    */content/drive/MyDrive/Project/MultiLabelData/GangavaramMultiLableResult.txt*
