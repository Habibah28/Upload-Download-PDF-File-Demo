package thescone.uploaddownloadfiledemo.Upload;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import java.util.UUID;

import thescone.uploaddownloadfiledemo.Config;
import thescone.uploaddownloadfiledemo.R;
import thescone.uploaddownloadfiledemo.Download.UploadedPDFs;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button buttonChooseFile;
    Button buttonUploadFile;
    TextView textViewFilePath;
    EditText editTextFileName;
    Button buttonNext;

    Uri filePath;

    //Pdf request code
    private int PICK_PDF_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonChooseFile = (Button) findViewById(R.id.buttonChooseFile);
        buttonUploadFile = (Button) findViewById(R.id.buttonUploadFile);
        textViewFilePath = (TextView) findViewById(R.id.textViewFilePath);
        editTextFileName = (EditText) findViewById(R.id.editTextFileName);
        buttonNext = (Button) findViewById(R.id.buttonNext);

        buttonChooseFile.setOnClickListener(this);
        buttonUploadFile.setOnClickListener(this);
        buttonNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.buttonChooseFile:
                showFileChooser();
                break;
            case R.id.buttonUploadFile:
                uploadMultipart();
                break;
            case R.id.buttonNext:
                startActivity(new Intent(this, UploadedPDFs.class));
                break;
        }
    }

    public void uploadMultipart() {
        //getting name for the image
        if (editTextFileName.getText().toString().isEmpty()) {
            editTextFileName.setError("Required");
        } else {
            //getting the actual path of the image
            String path = FilePath.getPath(this, filePath);
            String name = editTextFileName.getText().toString();

            if (path == null) {

                Toast.makeText(this, "Please move your .pdf file to internal storage and retry", Toast.LENGTH_LONG).show();
            } else {
                //Uploading code
                try {
                    String uploadId = UUID.randomUUID().toString();

                    //Creating a multi part request
                    new MultipartUploadRequest(this, uploadId, Config.UPLOAD_URL)
                            .addFileToUpload(path, "pdf") //Adding file
                            .addParameter("name", name) //Adding text parameter to the request
                            .setNotificationConfig(new UploadNotificationConfig())
                            .setMaxRetries(2)
                            .startUpload(); //Starting the upload

                } catch (Exception exc) {
                    Toast.makeText(this, exc.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }


    }

    public void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Pdf"), PICK_PDF_REQUEST);
    }

    //handling the image chooser activity result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_PDF_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            textViewFilePath.setText(String.valueOf(filePath));
        }
    }
}
