package com.example.homework_3

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.homework_3.adapter.ProductAdapter
import com.example.homework_3.model.Product
import com.google.firebase.database.*

class ProductsFragment : Fragment(R.layout.fragment_products) {

    private lateinit var recyclerView: RecyclerView
    private val productList = mutableListOf<Product>()
    private lateinit var adapter: ProductAdapter
    private lateinit var databaseRef: DatabaseReference

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.products_recyclerview)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        adapter = ProductAdapter(productList) { product ->
            Toast.makeText(
                requireContext(),
                "Clicked: ${product.name} - $${product.price}",
                Toast.LENGTH_SHORT
            ).show()
        }

        recyclerView.adapter = adapter

        databaseRef = FirebaseDatabase.getInstance().getReference("0/products")

        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                productList.clear()

                for (productSnap in snapshot.children) {
                    val product = productSnap.getValue(Product::class.java)
                    if (product != null) {
                        productList.add(product)
                    }
                }

                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), error.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}
