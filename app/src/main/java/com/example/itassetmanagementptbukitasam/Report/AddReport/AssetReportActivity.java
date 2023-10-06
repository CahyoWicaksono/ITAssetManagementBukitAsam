package com.example.itassetmanagementptbukitasam.Report.AddReport;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.itassetmanagementptbukitasam.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AssetReportActivity extends AppCompatActivity {
    EditText etName, etEmail, etJmlOne, etJmlTwo,etJmlThree, etJmlFour, etJmlFive, etJmlSix,
            etJmlSeven, etJmlEight, etJmlNine, etJmlTen, etJmlEleven, etJmlTweleve, etJmlThrteen, etJmlFurteen,
            etJmlFiveTeen, etJmlSixteen, etJmlSeventeen,etJmlEightTeen,etJmlNineteen, etJmlTwenty, etJmlTwentyOne,etStatus, etCondition;
    Spinner itemSpinnerOne, itemSpinnerTwo, itemSpinnerThree, itemSpinnerFour,itemSpinnerFive, itemSpinnerSix, itemSpinnerSeven, itemSpinnerEight,
            itemSpinnerNine, itemSpinnerTen,itemSpinnerEleven,itemSpinnerTveleve, itemSpinnerThrteen, itemSpinnerFourteen,itemSpinnerFiveteen,
            itemSpinnerSixteen, itemSpinnerSeventeen, itemSpinnerEighteen,itemSpinnerNineTeen, itemSpinnerTwenty,itemSpinnerTwentyOne;
    Button btnPrint;
    Bitmap bitmap, scaleBitmap;
    int pageWidth = 1200;
    Date dateTime;
    DateFormat dateFormat;
    String Used,notUsed ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_report_asset);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etNoTlp);
        etJmlOne = findViewById(R.id.etJmlOne);
        etJmlTwo = findViewById(R.id.etJmlTwo);
        etJmlThree = findViewById(R.id.etJmlThree);
        etJmlFour = findViewById(R.id.etJmlFour);
        etJmlFive = findViewById(R.id.etJmlFive);
        etJmlSix = findViewById(R.id.etJmlSix);
        etJmlSeven = findViewById(R.id.etJmlseven);
        etJmlEight = findViewById(R.id.etJmlEight);
        etJmlNine = findViewById(R.id.etJmlNine);
        etJmlTen = findViewById(R.id.etJmlTen);
        etJmlEleven = findViewById(R.id.etJmlEleven);
        etJmlTweleve = findViewById(R.id.etJmlTweleve);
        etJmlThrteen = findViewById(R.id.etJmlThrteen);
        etJmlFurteen = findViewById(R.id.etJmlFourteen);
        etJmlFiveTeen = findViewById(R.id.etJmlFiveteen);
        etJmlSixteen = findViewById(R.id.etJmlSixteen);
        etJmlSeventeen = findViewById(R.id.etJmlSvnteen);
        etJmlEightTeen = findViewById(R.id.etJmlEightTeen);
        etJmlNineteen = findViewById(R.id.etJmlNineTeen);
        etJmlTwenty = findViewById(R.id.etJmlTwenty);
        etJmlTwentyOne = findViewById(R.id.etJmlTwentyOne);




        etStatus = findViewById(R.id.etStatus);
        etCondition = findViewById(R.id.etCondition);


        //Spinner
        itemSpinnerOne = findViewById(R.id.itemSpinner3);
        itemSpinnerTwo = findViewById(R.id.itemSpinner4);
        itemSpinnerThree = findViewById(R.id.itemSpinner5);
        itemSpinnerFour = findViewById(R.id.itemSpinner6);
        itemSpinnerFive = findViewById(R.id.itemSpinner7);
        itemSpinnerSix = findViewById(R.id.itemSpinner8);
        itemSpinnerSeven = findViewById(R.id.itemSpinner9);
        itemSpinnerEight = findViewById(R.id.itemSpinner10);
        itemSpinnerNine = findViewById(R.id.itemSpinner11);
        itemSpinnerTen = findViewById(R.id.itemSpinner12);
        itemSpinnerEleven = findViewById(R.id.itemSpinner13);
        itemSpinnerTveleve = findViewById(R.id.itemSpinner14);
        itemSpinnerThrteen = findViewById(R.id.itemSpinner15);
        itemSpinnerFourteen = findViewById(R.id.itemSpinner16);
        itemSpinnerFiveteen = findViewById(R.id.itemSpinner17);
        itemSpinnerSixteen = findViewById(R.id.itemSpinner18);
        itemSpinnerSeventeen = findViewById(R.id.itemSpinner19);
        itemSpinnerEighteen = findViewById(R.id.itemSpinner20);
        itemSpinnerNineTeen = findViewById(R.id.itemSpinner21);
        itemSpinnerTwenty = findViewById(R.id.itemSpinner22);
        itemSpinnerTwentyOne = findViewById(R.id.itemSpinner23);



        btnPrint = findViewById(R.id.btnPrint);

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bukitasam);
        scaleBitmap = Bitmap.createScaledBitmap(bitmap, 500, 318, false);

        //permission
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        createPDF();
    }
    private void createPDF() {
        btnPrint.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SimpleDateFormat")
            @Override
            public void onClick(View v) {

                dateTime = new Date();

                //get input
                if (etName.getText().toString().length() == 0 ||
                        etEmail.getText().toString().length() == 0 ||
                        etJmlOne.getText().toString().length() == 0 ||
                        etJmlTwo.getText().toString().length() == 0 ||
                        etJmlThree.getText().toString().length() == 0 ||
                        etJmlFour.getText().toString().length() == 0 ||
                        etJmlFive.getText().toString().length() == 0 ||
                        etJmlSix.getText().toString().length() == 0 ||
                        etJmlSeven.getText().toString().length() == 0 ||
                        etJmlEight.getText().toString().length() == 0 ||
                        etJmlNine.getText().toString().length() == 0 ||
                        etJmlTen.getText().toString().length() == 0 ||
                        etJmlEleven.getText().toString().length() == 0 ||
                        etJmlTweleve.getText().toString().length() == 0 ||
                        etJmlThrteen.getText().toString().length() == 0 ||
                        etJmlFurteen.getText().toString().length() == 0 ||
                        etJmlFiveTeen.getText().toString().length() == 0 ||
                        etJmlSixteen.getText().toString().length() == 0 ||
                        etJmlSeventeen.getText().toString().length() == 0 ||
                        etJmlEightTeen.getText().toString().length() == 0 ||
                        etJmlNineteen.getText().toString().length() == 0 ||
                        etJmlTwenty.getText().toString().length() == 0 ||
                        etJmlTwentyOne.getText().toString().length() == 0 ||
                        etStatus.getText().toString().length() == 0 ||
                        etCondition.getText().toString().length() == 0) {
                    Toast.makeText(AssetReportActivity.this, "Please fill in the data completely!", Toast.LENGTH_LONG).show();
                } else {

                    PdfDocument pdfDocument = new PdfDocument();
                    Paint paint = new Paint();
                    Paint titlePaint = new Paint();

                    PdfDocument.PageInfo pageInfo
                            = new PdfDocument.PageInfo.Builder(1200, 3000, 2).create();
                    PdfDocument.Page page = pdfDocument.startPage(pageInfo);

                    Canvas canvas = page.getCanvas();
                    canvas.drawBitmap(scaleBitmap, 0, 0, paint);

                    titlePaint.setTextAlign(Paint.Align.CENTER);
                    titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                    titlePaint.setColor(Color.BLACK);
                    titlePaint.setTextSize(50);
                    canvas.drawText("Asset Report", pageWidth / 2, 400, titlePaint);

                    paint.setTextAlign(Paint.Align.LEFT);
                    paint.setColor(Color.BLACK);
                    paint.setTextSize(35f);
                    canvas.drawText("Name : " + etName.getText(), 20, 590, paint);
                    canvas.drawText("Gmail : " + etEmail.getText(), 20, 640, paint);

                    paint.setTextAlign(Paint.Align.RIGHT);
                    canvas.drawText("From : " + "Logistic", pageWidth - 20, 590, paint);

                    dateFormat = new SimpleDateFormat("dd/MM/yy");
                    canvas.drawText("Date: " + dateFormat.format(dateTime), pageWidth - 20, 640, paint);

                    dateFormat = new SimpleDateFormat("HH:mm:ss");
                    canvas.drawText("Time: " + dateFormat.format(dateTime), pageWidth - 20, 690, paint);

                    paint.setStyle(Paint.Style.STROKE);
                    paint.setStrokeWidth(2);
                    canvas.drawRect(20, 780, pageWidth - 20, 860, paint);

                    paint.setTextAlign(Paint.Align.LEFT);
                    paint.setStyle(Paint.Style.FILL);
                    canvas.drawText("No.", 40, 830, paint);
                    canvas.drawText("Asset Name", 200, 830, paint);
                    canvas.drawText("Status", 700, 830, paint);
                    canvas.drawText("Amount", 900, 830, paint);
                    canvas.drawText("Cond.", 1050, 830, paint);

                    canvas.drawLine(180, 790, 180, 840, paint);
                    canvas.drawLine(680, 790, 680, 840, paint);
                    canvas.drawLine(880, 790, 880, 840, paint);
                    canvas.drawLine(1030, 790, 1030, 840, paint);

                    if (itemSpinnerOne.getSelectedItemPosition() != 0) {
                        canvas.drawText("1.", 40, 900, paint);
                        canvas.drawText(itemSpinnerOne.getSelectedItem().toString(), 200, 900, paint);
                        canvas.drawText(etStatus.getText().toString(), 700, 900, paint);
                        canvas.drawText(etJmlOne.getText().toString(), 900, 900, paint);
                        paint.setTextAlign(Paint.Align.RIGHT);
                        canvas.drawText(etCondition.getText().toString(), pageWidth - 40, 900, paint);
                        paint.setTextAlign(Paint.Align.LEFT);

                    }
                    if (itemSpinnerTwo.getSelectedItemPosition() != 0) {
                        canvas.drawText("2.", 40, 1000, paint);
                        canvas.drawText(itemSpinnerTwo.getSelectedItem().toString(), 200, 1000, paint);
                        canvas.drawText(etStatus.getText().toString(), 700, 1000, paint);
                        canvas.drawText(etJmlTwo.getText().toString(), 900, 1000, paint);
                        paint.setTextAlign(Paint.Align.RIGHT);
                        canvas.drawText(etCondition.getText().toString(), pageWidth - 40, 1000, paint);
                        paint.setTextAlign(Paint.Align.LEFT);

                    }
                    if (itemSpinnerThree.getSelectedItemPosition() != 0) {
                        canvas.drawText("3.", 40, 1100, paint);
                        canvas.drawText(itemSpinnerThree.getSelectedItem().toString(), 200, 1100, paint);
                        canvas.drawText(etStatus.getText().toString(), 700, 1100, paint);
                        canvas.drawText(etJmlThree.getText().toString(), 900, 1100, paint);
                        paint.setTextAlign(Paint.Align.RIGHT);
                        canvas.drawText(etCondition.getText().toString(), pageWidth - 40, 1100, paint);
                        paint.setTextAlign(Paint.Align.LEFT);

                    }
                    if (itemSpinnerFour.getSelectedItemPosition() != 0) {
                        canvas.drawText("4.", 40, 1200, paint);
                        canvas.drawText(itemSpinnerFour.getSelectedItem().toString(), 200, 1200, paint);
                        canvas.drawText(etStatus.getText().toString(), 700, 1200, paint);
                        canvas.drawText(etJmlFour.getText().toString(), 900, 1200, paint);
                        paint.setTextAlign(Paint.Align.RIGHT);
                        canvas.drawText(etCondition.getText().toString(), pageWidth - 40, 1200, paint);
                        paint.setTextAlign(Paint.Align.LEFT);

                    }

                    if (itemSpinnerFive.getSelectedItemPosition() != 0) {
                        canvas.drawText("5.", 40, 1300, paint);
                        canvas.drawText(itemSpinnerFive.getSelectedItem().toString(), 200, 1300, paint);
                        canvas.drawText(etStatus.getText().toString(), 700, 1300, paint);
                        canvas.drawText(etJmlFive.getText().toString(), 900, 1300, paint);
                        paint.setTextAlign(Paint.Align.RIGHT);
                        canvas.drawText(etCondition.getText().toString(), pageWidth - 40, 1300, paint);
                        paint.setTextAlign(Paint.Align.LEFT);

                    }
                    if (itemSpinnerSix.getSelectedItemPosition() != 0) {
                        canvas.drawText("6.", 40, 1400, paint);
                        canvas.drawText(itemSpinnerSix.getSelectedItem().toString(), 200, 1400, paint);
                        canvas.drawText(etStatus.getText().toString(), 700, 1400, paint);
                        canvas.drawText(etJmlSix.getText().toString(), 900, 1400, paint);
                        paint.setTextAlign(Paint.Align.RIGHT);
                        canvas.drawText(etCondition.getText().toString(), pageWidth - 40, 1400, paint);
                        paint.setTextAlign(Paint.Align.LEFT);

                    }


                    if (itemSpinnerSeven.getSelectedItemPosition() != 0) {
                        canvas.drawText("7.", 40, 1500, paint);
                        canvas.drawText(itemSpinnerSeven.getSelectedItem().toString(), 200, 1500, paint);
                        canvas.drawText(etStatus.getText().toString(), 700, 1500, paint);
                        canvas.drawText(etJmlSeven.getText().toString(), 900, 1500, paint);
                        paint.setTextAlign(Paint.Align.RIGHT);
                        canvas.drawText(etCondition.getText().toString(), pageWidth - 40, 1500, paint);
                        paint.setTextAlign(Paint.Align.LEFT);

                    }
                    if (itemSpinnerEight.getSelectedItemPosition() != 0) {
                        canvas.drawText("8.", 40, 1600, paint);
                        canvas.drawText(itemSpinnerEight.getSelectedItem().toString(), 200, 1600, paint);
                        canvas.drawText(etStatus.getText().toString(), 700, 1600, paint);
                        canvas.drawText(etJmlEight.getText().toString(), 900, 1600, paint);
                        paint.setTextAlign(Paint.Align.RIGHT);
                        canvas.drawText(etCondition.getText().toString(), pageWidth - 40, 1600, paint);
                        paint.setTextAlign(Paint.Align.LEFT);

                    }
                    if (itemSpinnerNine.getSelectedItemPosition() != 0) {
                        canvas.drawText("9.", 40, 1700, paint);
                        canvas.drawText(itemSpinnerNine.getSelectedItem().toString(), 200, 1700, paint);
                        canvas.drawText(etStatus.getText().toString(), 700, 1700, paint);
                        canvas.drawText(etJmlNine.getText().toString(), 900, 1700, paint);
                        paint.setTextAlign(Paint.Align.RIGHT);
                        canvas.drawText(etCondition.getText().toString(), pageWidth - 40, 1700, paint);
                        paint.setTextAlign(Paint.Align.LEFT);

                    }
                    if (itemSpinnerTen.getSelectedItemPosition() != 0) {
                        canvas.drawText("10.", 40, 1800, paint);
                        canvas.drawText(itemSpinnerTen.getSelectedItem().toString(), 200, 1800, paint);
                        canvas.drawText(etStatus.getText().toString(), 700, 1800, paint);
                        canvas.drawText(etJmlTen.getText().toString(), 900, 1800, paint);
                        paint.setTextAlign(Paint.Align.RIGHT);
                        canvas.drawText(etCondition.getText().toString(), pageWidth - 40, 1800, paint);
                        paint.setTextAlign(Paint.Align.LEFT);

                    }

                    if (itemSpinnerEleven.getSelectedItemPosition() != 0) {
                        canvas.drawText("11.", 40, 1900, paint);
                        canvas.drawText(itemSpinnerEleven.getSelectedItem().toString(), 200, 1900, paint);
                        canvas.drawText(etStatus.getText().toString(), 700, 1900, paint);
                        canvas.drawText(etJmlEleven.getText().toString(), 900, 1900, paint);
                        paint.setTextAlign(Paint.Align.RIGHT);
                        canvas.drawText(etCondition.getText().toString(), pageWidth - 40, 1900, paint);
                        paint.setTextAlign(Paint.Align.LEFT);

                    }

                    if (itemSpinnerTveleve.getSelectedItemPosition() != 0) {
                        canvas.drawText("12.", 40, 2000, paint);
                        canvas.drawText(itemSpinnerTveleve.getSelectedItem().toString(), 200, 2000, paint);
                        canvas.drawText(etStatus.getText().toString(), 700, 2000, paint);
                        canvas.drawText(etJmlTweleve.getText().toString(), 900, 2000, paint);
                        paint.setTextAlign(Paint.Align.RIGHT);
                        canvas.drawText(etCondition.getText().toString(), pageWidth - 40, 2000, paint);
                        paint.setTextAlign(Paint.Align.LEFT);

                    }
                    if (itemSpinnerThrteen.getSelectedItemPosition() != 0) {
                        canvas.drawText("13.", 40, 2100, paint);
                        canvas.drawText(itemSpinnerThrteen.getSelectedItem().toString(), 200, 2100, paint);
                        canvas.drawText(etStatus.getText().toString(), 700, 2100, paint);
                        canvas.drawText(etJmlThrteen.getText().toString(), 900, 2100, paint);
                        paint.setTextAlign(Paint.Align.RIGHT);
                        canvas.drawText(etCondition.getText().toString(), pageWidth - 40, 2100, paint);
                        paint.setTextAlign(Paint.Align.LEFT);

                    }
                    if (itemSpinnerFourteen.getSelectedItemPosition() != 0) {
                        canvas.drawText("14.", 40, 2200, paint);
                        canvas.drawText(itemSpinnerFourteen.getSelectedItem().toString(), 200, 2200, paint);
                        canvas.drawText(etStatus.getText().toString(), 700, 2200, paint);
                        canvas.drawText(etJmlFurteen.getText().toString(), 900, 2200, paint);
                        paint.setTextAlign(Paint.Align.RIGHT);
                        canvas.drawText(etCondition.getText().toString(), pageWidth - 40, 2200, paint);
                        paint.setTextAlign(Paint.Align.LEFT);

                    }
                    if (itemSpinnerFiveteen.getSelectedItemPosition() != 0) {
                        canvas.drawText("15.", 40, 2300, paint);
                        canvas.drawText(itemSpinnerFiveteen.getSelectedItem().toString(), 200, 2300, paint);
                        canvas.drawText(etStatus.getText().toString(), 700, 2300, paint);
                        canvas.drawText(etJmlFiveTeen.getText().toString(), 900, 2300, paint);
                        paint.setTextAlign(Paint.Align.RIGHT);
                        canvas.drawText(etCondition.getText().toString(), pageWidth - 40, 2300, paint);
                        paint.setTextAlign(Paint.Align.LEFT);

                    }
                    if (itemSpinnerSixteen.getSelectedItemPosition() != 0) {
                        canvas.drawText("16.", 40, 2400, paint);
                        canvas.drawText(itemSpinnerSixteen.getSelectedItem().toString(), 200, 2400, paint);
                        canvas.drawText(etStatus.getText().toString(), 700, 2400, paint);
                        canvas.drawText(etJmlSixteen.getText().toString(), 900, 2400, paint);
                        paint.setTextAlign(Paint.Align.RIGHT);
                        canvas.drawText(etCondition.getText().toString(), pageWidth - 40, 2400, paint);
                        paint.setTextAlign(Paint.Align.LEFT);

                    }

                    if (itemSpinnerSeventeen.getSelectedItemPosition() != 0) {
                        canvas.drawText("17.", 40, 2500, paint);
                        canvas.drawText(itemSpinnerSeventeen.getSelectedItem().toString(), 200, 2500, paint);
                        canvas.drawText(etStatus.getText().toString(), 700, 2500, paint);
                        canvas.drawText(etJmlSeventeen.getText().toString(), 900, 2500, paint);
                        paint.setTextAlign(Paint.Align.RIGHT);
                        canvas.drawText(etCondition.getText().toString(), pageWidth - 40, 2500, paint);
                        paint.setTextAlign(Paint.Align.LEFT);

                    }
                    if (itemSpinnerEighteen.getSelectedItemPosition() != 0) {
                        canvas.drawText("18.", 40, 2600, paint);
                        canvas.drawText(itemSpinnerEighteen.getSelectedItem().toString(), 200, 2600, paint);
                        canvas.drawText(etStatus.getText().toString(), 700, 2600, paint);
                        canvas.drawText(etJmlEightTeen.getText().toString(), 900, 2600, paint);
                        paint.setTextAlign(Paint.Align.RIGHT);
                        canvas.drawText(etCondition.getText().toString(), pageWidth - 40, 2600, paint);
                        paint.setTextAlign(Paint.Align.LEFT);

                    }


                    if (itemSpinnerNineTeen.getSelectedItemPosition() != 0) {
                        canvas.drawText("19.", 40, 2700, paint);
                        canvas.drawText(itemSpinnerNineTeen.getSelectedItem().toString(), 200, 2700, paint);
                        canvas.drawText(etStatus.getText().toString(), 700, 2700, paint);
                        canvas.drawText(etJmlNineteen.getText().toString(), 900, 2700, paint);
                        paint.setTextAlign(Paint.Align.RIGHT);
                        canvas.drawText(etCondition.getText().toString(), pageWidth - 40, 2700, paint);
                        paint.setTextAlign(Paint.Align.LEFT);

                    }
                    if (itemSpinnerTwenty.getSelectedItemPosition() != 0) {
                        canvas.drawText("20.", 40, 2800, paint);
                        canvas.drawText(itemSpinnerTwenty.getSelectedItem().toString(), 200, 2800, paint);
                        canvas.drawText(etStatus.getText().toString(), 700, 2800, paint);
                        canvas.drawText(etJmlTwenty.getText().toString(), 900, 2800, paint);
                        paint.setTextAlign(Paint.Align.RIGHT);
                        canvas.drawText(etCondition.getText().toString(), pageWidth - 40, 2800, paint);
                        paint.setTextAlign(Paint.Align.LEFT);

                    }
                    if (itemSpinnerTwentyOne.getSelectedItemPosition() != 0) {
                        canvas.drawText("21.", 40, 2900, paint);
                        canvas.drawText(itemSpinnerTwentyOne.getSelectedItem().toString(), 200, 2900, paint);
                        canvas.drawText(etStatus.getText().toString(), 700, 2900, paint);
                        canvas.drawText(etJmlTwentyOne.getText().toString(), 900, 2900, paint);
                        paint.setTextAlign(Paint.Align.RIGHT);
                        canvas.drawText(etCondition.getText().toString(), pageWidth - 40, 2900, paint);
                        paint.setTextAlign(Paint.Align.LEFT);

                    }


                    pdfDocument.finishPage(page);

                    File file = new File(Environment.getExternalStorageDirectory(), "/Asset Report "+dateTime+".pdf");
                    try {
                        pdfDocument.writeTo(new FileOutputStream(file));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    pdfDocument.close();
                    Toast.makeText(AssetReportActivity.this, "Create Asset Report Successfully", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
