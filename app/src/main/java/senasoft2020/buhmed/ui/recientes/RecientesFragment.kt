package senasoft2020.buhmed.ui.recientes

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import senasoft2020.buhmed.Post
import senasoft2020.buhmed.PostAdapter
import senasoft2020.buhmed.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RecientesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class RecientesFragment : Fragment() {


    val db = FirebaseFirestore.getInstance()

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_recientes, container, false)

        val postList = ArrayList<Post>()
        db.collection("publicaciones").addSnapshotListener { snapshot, error ->
            if (error != null){
                Log.e("error","${error.message}")
                return@addSnapshotListener
            }
            for (documentos in snapshot!!){
                val publicaciones = documentos.toObject(Post::class.java)
                postList.add(publicaciones)
            }
            Log.d("documentos","${postList}")
        }

//        val postList = ArrayList<Post>()
//        db.collection("publicaciones").whereEqualTo("Categoria", "Movilidad").get()
//            .addOnSuccessListener {
//                for (documento in it) {
//                    val publicaciones = documento.toObject(Post::class.java)
//                    postList.add(publicaciones)
//
//                }
//                Log.d("doc", "${postList}")
//                Log.d("doc", "${postList[0].Titulo}")
//            }


        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerViewRecientes)
        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        val adapter = PostAdapter(postList)
        recyclerView.adapter = adapter
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RecientesFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RecientesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


}