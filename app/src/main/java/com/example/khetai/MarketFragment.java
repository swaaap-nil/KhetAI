package com.example.khetai;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.khetai.adapter.GroupAdapter;
import com.example.khetai.adapter.Trio;
import com.example.khetai.model.Crop;
import com.example.khetai.model.Group;
import com.example.khetai.model.Product;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MarketFragment extends Fragment implements RecyclerViewInterface{


      private Context mContext;
    private RecyclerView recyclerView;

    private GroupAdapter adapter;
    private List<Group> groupList;
    private List<Product> featuredList;
    private List<Product> recommendList;
    ArrayList<Product> productList;
    ArrayList<Product> productList1;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Gson gson = new Gson();
    View view;

    public MarketFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         view= inflater.inflate(R.layout.fragment_market, container, false);
        recyclerView=view.findViewById(R.id.market_RV);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setAdapterType(view);
        setAdapter();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext=context;
    }

    private void setAdapter() {
        initGroupData();
        initPlantData();
        createProductList();
        saveProductListToStorage();
        
        adapter = new GroupAdapter(mContext,groupList,productList1,productList,this);
        recyclerView.setAdapter(adapter);
    }

    private void saveProductListToStorage() {
        editor= preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(productList);
        editor.putString("myProductList", json);
        editor.commit();

    }
    private void initPlantData() {

        recommendList = new ArrayList<>();
        featuredList = new ArrayList<>();
        productList1 = new ArrayList<>();
        productList1.add(new Product( "Urea",  200.0,"ghfhgf",R.drawable.urea));
    }

    private void createProductList(){

        ArrayList<Product> completeProductList = new ArrayList<>();
        completeProductList.add(new Product("NPK 13-0-45",300.0,"laude ka desc",R.drawable.npk));
        completeProductList.add(new Product("NPK 19-19-19",550.0,"laude ka desc",R.drawable.npk));
        completeProductList.add(new Product("NPK 12-61-0",1350.0,"laude ka desc",R.drawable.npk));
        completeProductList.add(new Product("Vermicompost",192.0,"laude ka desc",R.drawable.vermicompost));
        completeProductList.add(new Product("Poultry Manure",112.0,"laude ka desc",R.drawable.farm_yard_manure));
        completeProductList.add(new Product("NPK 15-15-30",112.0,"laude ka desc",R.drawable.npk));
        completeProductList.add(new Product("Neem Cake",870.0,"ghfhgf",R.drawable.neem_cake));
        completeProductList.add(new Product("NPK 20-10-10",540.0,"ghfhgf",R.drawable.npk));
        completeProductList.add(new Product("Cow Dung",17.0,"ghfhgf",R.drawable.cowdung));
        completeProductList.add(new Product("NPK 13-0-45",1250.0,"ghfhgf",R.drawable.npk));
        completeProductList.add(new Product("NPK 0-0-50",180.0,"ghfhgf",R.drawable.npk));
        completeProductList.add(new Product("Urea",200.0,"ghfhgf",R.drawable.urea));
        completeProductList.add(new Product("NPK 10-52-10",400.0,"ghfhgf",R.drawable.npk));
        completeProductList.add(new Product("NPK 2-1-2",400.0,"ghfhgf",R.drawable.npk));
        completeProductList.add(new Product("Rhizobium",1200.0,"ghfhgf",R.drawable.npk));
        completeProductList.add(new Product("Farm Yard Manure",550.0,"ghfhgf",R.drawable.farm_yard_manure));


        preferences= getActivity().getSharedPreferences("FormFilled", MODE_PRIVATE);
        String json = preferences.getString("myCropList", null);
        ArrayList<Crop> myCropList = gson.fromJson(json,
                new TypeToken<List<Crop>>() {
                }.getType());

        //add products name to user's product list
        productList = new ArrayList<>();
        for (Crop crop:myCropList) {
                ArrayList<Trio<String,Integer, Integer>> trioList = crop.getInputs();
                for(int i = 0; i< trioList.size(); i++){
                    Trio trio = trioList.get(i);
                           if(!productList.contains(new Product((String) trio.getKey()))){
                               Log.d("Match", trio.getKey()+"");
                               productList.add(new Product((String) trio.getKey()));
                    }
                }

        }
        Log.d("index1","" + productList.size());
        //now add details to all the products

        for (int i = 0; i < productList.size(); i++) {
            for(int j=0;j< completeProductList.size();j++){
                Product incompleteProduct = productList.get(i);
                Product completeProduct = completeProductList.get(j);

                if(incompleteProduct.equals(completeProduct)){
                   incompleteProduct.setProductImage(completeProduct.getProductImage());
                   incompleteProduct.setProductPrice(completeProduct.getProductPrice());
                   break;
                }
            }
        }


    }
    private void initGroupData() {
        groupList = new ArrayList<>();
        groupList.add(new Group("Your List","More"));
        groupList.add(new Group("Recommendation","More"));
    }

    private void setAdapterType(View view) {
        recyclerView = view.findViewById(R.id.market_RV);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
    }

    @Override
    public void onItemClick(int position) {
//        Intent intent = new Intent(getContext(),ProductDetailsActivity.class);
//        intent.putExtra("clickedProductName", (CharSequence) recommendList.get(position));
//        getActivity().startActivity(intent);

    }
}