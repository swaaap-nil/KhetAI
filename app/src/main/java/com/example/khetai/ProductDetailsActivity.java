package com.example.khetai;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.khetai.adapter.Trio;
import com.example.khetai.model.Crop;
import com.example.khetai.model.Product;
import com.google.android.material.chip.Chip;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProductDetailsActivity extends AppCompatActivity {

    Chip chip4;
    Chip chip5;
    int quantity=1;
    ImageView minus,plus;
    TextView textViewQuantity,ProductDescription;
    SharedPreferences preferences;
    ArrayList<Product> myProductList;
    Product clickedProduct;
    ArrayList<Crop> myCropList;
    String clickedProductName;
    Gson gson =new Gson();
    Integer requiredQuantity=0;
    HashMap<String,String> hashMap = new HashMap<>();
    @SuppressLint({"SuspiciousIndentation", "SetTextI18n"})
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_product_details);

        chip4=findViewById(R.id.chip4);
        chip5=findViewById(R.id.chip5);
        minus=findViewById(R.id.floatingActionButton1);
        plus=findViewById(R.id.floatingActionButton2);
        textViewQuantity=findViewById(R.id.textView_quantity);
        TextView RequiredQuantity = findViewById(R.id.required_quantity);
        TextView ProductName = findViewById(R.id.name_heading);
        TextView ProductPrice = findViewById(R.id.price_heading);
        ImageView ProductImage = findViewById(R.id.product_image);
        ProductDescription = findViewById(R.id.productDescription);

        Bundle bundle = getIntent().getExtras();
        clickedProductName = bundle.getString("clickedProductName");
        Log.d("clickedProductName",""+clickedProductName);

        getProductAndCropListFromStorage();
        identifyClickedProduct();
        computeRequiredQuantity();
        prepareHashMap();

        //setting details on the page
        ProductName.setText(clickedProductName);
        ProductPrice.setText("Price : ₹" + clickedProduct.getProductPrice()+ " / 10kg");
        RequiredQuantity.setText(requiredQuantity + "kg");
        ProductImage.setImageResource(clickedProduct.getProductImage());

        Log.d("desc",""+clickedProduct.getProductPrice());


        ProductDescription.setText(""+ hashMap.get(clickedProductName));

        chip4.setOnClickListener(v -> chip5.setChecked(false));

        chip5.setOnClickListener(v -> chip4.setChecked(false));

        minus.setOnClickListener(v -> {
            if(quantity-1>0)
            textViewQuantity.setText(--quantity+"");
        });

        plus.setOnClickListener(v -> textViewQuantity.setText(++quantity+""));
    }

    private void prepareHashMap() {


        hashMap.put("Urea","Urea is a source of Nitrogen, an\n" +
                "essential nutrient crucial for crop\n" +
                "growth and development. Urea is the\n" +
                "most important nitrogenous fertilizer in\n" +
                "the country because of its high N\n" +
                "content (46%N). It also has industrial\n" +
                "applications such as the production of\n" +
                "plastics and as a nutritional supplement\n" +
                "for cattle.");
        hashMap.put("NPK 10-52-10","High Phosphate Coir Fertiliser is a fully\n" +
                "water soluble fertiliser that is high in\n" +
                "phosphate, added nitrogen and potash\n" +
                "with trace elements (Including,\n" +
                "Magnesium Oxide, Boron, Copper, Iron,\n" +
                "Manganese, Molybdenum and Zinc).");
        hashMap.put("NOK 0-0-50","Nitrogen, Potassium and Phosphorous provide nutrients for growth of seeds, roots and stem development. NPK 0 0 50 fertilizer also helps in increasing the size and number of the flowers. It also helps in increasing and size of the fruits.");

        hashMap.put("NPK 15-15-30","NPK Granular fertilizer 15-15-15+TE is the primary macronutrient: Nitrogen (N), Phosphorus (P), and Potassium (K) which stimulates healthy growth in Vegetables, Fruit plants, Trees, Indoor Plants, and Animal fodder. Nitrogen (N) – Nitrogen is largely responsible for the growth of leaves on the plant.");
        hashMap.put("NPK 2-1-2","This liquid organic fertilizer is highly soluble and compatible with injector systems and drip irrigation. It is made from grains fermented with lactobacillus. It contains highly bio-available forms of nitrogen, phosphorous and potassium as well as humic acid, microbial and bio-active compounds.");
        hashMap.put("NPK 10-20-10","NPK 20 : 10 : 10 fertilizers can be used on all soil types under all crops. It is used as the basic fertilizer in Nigeria to feed the plants. NPK 20 : 10 : 10 fertilizers is important for plant growth in the early.\n" +
                "•\tBalanced Fertilizer - provides all primary nutrients (Nitrogen,) Phosphorus & Potash) essential for plant growth & development.\n" +
                "•\tGranulated form-does not disperse in water.\n" +
                "•\tCan be easily sown with seeds.\n" +
                "•\tNegates need or higher grade fertilizers, reduces cost, augments returns.\n");
        hashMap.put("Neem Cake","Neem cake organic manure is the by-product obtained in the process of cold pressing of neem tree fruits and kernels, and the solvent extraction process for neem oil cake. It is a potential source of organic manure under the Bureau of Indian Standards, Specification No. 8558. Neem has demonstrated considerable potential as a fertilizer. For this purpose, neem cake and neem leaves are especially promising. Puri (1999), in his book on neem, has given details about neem seed cake as manure and nitrification inhibitor. The author has described that, after processing, neem cake can be used for partial replacement of poultry and cattle feed.");
        hashMap.put("NPK 12-61-0","•   100 % water soluble formula with balanced pH which helps feeding all type of crops.\n" +
                "•  • It is very useful for timely maturity of crops and also increase in productivity.\n" +
                "•  • It contains high concentration of Phosphorus along with Nitrogen to boost early vegetative growth as well as root growth of most of the plants.\n" +
                "•  • Its solutions has stabilized Ph due to buffering effect which makes ready availability of micro nutrients.\n" +
                "•  • It boosts bud break and initiates flowering. It also increases roots.\n" +
                "•  • Increases tillering in grain crops.\n");

        hashMap.put("NPK 19-19-19","It is a free flowing, 100% water-soluble product and recommended to used for drip Fertigation or foliar spray.\n" +
                "\n" +
                "Contains Nitrogen, Phosphorus and Potassium in equal ration.\n" +
                "\n" +
                "Dose:- 5 gms per litre of water by drip fertigation/spray.\n" +
                "•\tWater soluble complex fertilizer containing primary nutrients in a balanced ratio\n" +
                "•\tIt is used as a foliar spray on crops for higher yields\n");



        hashMap.put("NPK 13-0-45","This provides excellent green-up in low temperature conditions.  Apply in the fall for extra protection against drought and winter stress. N-P-K 13-00-45 is Water Soluble Fertilizer contributing nitrogen 13 % and potassium 45 %. Potassium nitrate is a unique source of potassium by its nutritional value and its contribution to the health and yields of plants. Potassium nitrate outperforms other potassium fertilizers on crops of all types. Potassium nitrate increases yields and improves quality in vegetables, field crops., flowers and fruit and nut trees. Potassium nitrate is an ideal source of N and K for optional plant nutrition. It is available in a variety of compositions and formulations, to suit specific crop requirements and growth environment");

        hashMap.put("NPK 20-10-10","NPK 20 : 10 : 10 fertilizers can be used on all soil types under all crops. It is used as the basic fertilizer in Nigeria to feed the plants. NPK 20 : 10 : 10 fertilizers is important for plant growth in the early.\n" +
                "•\tBalanced Fertilizer - provides all primary nutrients (Nitrogen,) Phosphorus & Potash) essential for plant growth & development.\n" +
                "•\tGranulated form-does not disperse in water.\n" +
                "•\tCan be easily sown with seeds.\n" +
                "•\tNegates need or higher grade fertilizers, reduces cost, augments returns.\n");

        hashMap.put("Poultry Manure","Poultry manure is an organic fertilizer, the most concentrated and fastest among other organic fertilizers. It refers to the local fertilizer, containing 30-50% in non-littered form, in littered form – about 10% of ammonia nitrogen from the total amount of nitrogen. Contents");

        hashMap.put("Vermicompost","Vermicomposting is an organic and biological process in which earthworm species are primarily used to convert organic matter or biodegradable wastes into manure. The produced vermicomposts are rich in nutrition and thus, they are widely used as bio fertilisers in organic farming and sewage treatment plants.\n" +
                "1.\tVermicompost improves the recycling of soil.\n" +
                "2.\tVermicompost enriches the soil with microbes.\n" +
                "3.\tIn rural areas, the production of vermicompost can provide livelihood support to the unemployed.\n" +
                "4.\tVermicompost helps in improving soil texture, aeration and increases water retention capacity.\n" +
                "5.\tVermicompost acts as a soil conditioner and improves the biological, physical and chemical properties of the soil.\n");
    }


    private void computeRequiredQuantity() {

        for (int i = 0; i < myCropList.size(); i++) {
            Crop currentCrop= myCropList.get(i);
            ArrayList<Trio<String,Integer, Integer>> productListForCurrentCrop = currentCrop.getInputs();

            for(int j=0;j<productListForCurrentCrop.size();j++) {
                Trio<String,Integer,Integer> currentTrio = productListForCurrentCrop.get(j);
                if(currentTrio.getKey().equalsIgnoreCase(clickedProductName)){
                    requiredQuantity = requiredQuantity + (Integer) currentTrio.getValue();
                }
            }
        }
    }

    private void identifyClickedProduct() {

        //identifying product
        for(int i=0 ;i<myProductList.size();i++){
            if(myProductList.get(i).equals(new Product(clickedProductName))){
                clickedProduct=myProductList.get(i);

                break;
            }
        }
    }

    private void getProductAndCropListFromStorage() {

        preferences= getSharedPreferences("FormFilled", MODE_PRIVATE);

        String json = preferences.getString("myProductList", null);
        myProductList = gson.fromJson(json,
                new TypeToken<List<Product>>() {
                }.getType());




        json = preferences.getString("myCropList", null);
        myCropList = gson.fromJson(json,
                new TypeToken<List<Crop>>() {
                }.getType());
    }
}
