package senasoft2020.buhmed.ui.recientes

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_recientes.*
import org.w3c.dom.Text
import senasoft2020.buhmed.Post
import senasoft2020.buhmed.PostAdapter
import senasoft2020.buhmed.R
import java.util.*
import kotlin.collections.ArrayList

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_recientes, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewRecientes)
        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        var postList = ArrayList<Post>()
        db.collection("publicaciones").addSnapshotListener { snapshot, error ->
            if (error != null){
                Log.e("error","${error.message}")
                return@addSnapshotListener
            }
            for (documentos in snapshot!!) {
                val publicaciones = documentos.toObject(Post::class.java)
                val myPost = Post()
                myPost.Id = documentos.id
                myPost.Autor = publicaciones.Autor
                myPost.Categoria = publicaciones.Categoria
                myPost.Descripcion = publicaciones.Descripcion
                myPost.Rate = publicaciones.Rate
                myPost.Titulo = publicaciones.Titulo
                postList.add(myPost)
            }
            val adapter = PostAdapter(postList)
            recyclerView.adapter = adapter
            Log.d("documentos", "${postList}")
        }
        val icSeguridad: ImageButton = view.findViewById(R.id.ic_seguridad)
        icSeguridad.setOnClickListener {
            Toast.makeText(context, "Filtrando, por favor espere.", Toast.LENGTH_SHORT).show()
            db.collection("publicaciones").whereEqualTo("Categoria", "Seguridad").addSnapshotListener { snapshot, error ->
                if (error != null){
                    Log.e("error","${error.message}")
                    return@addSnapshotListener
                }
                postList.clear()
                for (documento in snapshot!!) {
                    val publicaciones = documento.toObject(Post::class.java)
                    val myPost = Post()
                    myPost.Id = documento.id
                    myPost.Autor = publicaciones.Autor
                    myPost.Categoria = publicaciones.Categoria
                    myPost.Descripcion = publicaciones.Descripcion
                    myPost.Rate = publicaciones.Rate
                    myPost.Titulo = publicaciones.Titulo
                    postList.add(myPost)
                }
                Toast.makeText(context, "Hecho.", Toast.LENGTH_SHORT).show()
                val adapter = PostAdapter(postList)
                recyclerView.adapter = adapter
            }
        }
        val icMovilidad: ImageButton = view.findViewById(R.id.ic_movilidad)
        icMovilidad.setOnClickListener {
            Toast.makeText(context, "Filtrando, por favor espere.", Toast.LENGTH_SHORT).show()
            db.collection("publicaciones").whereEqualTo("Categoria", "Movilidad").addSnapshotListener { snapshot, error ->
                if (error != null){
                    Log.e("error","${error.message}")
                    return@addSnapshotListener
                }
                postList.clear()
                for (documento in snapshot!!) {
                    val publicaciones = documento.toObject(Post::class.java)
                    val myPost = Post()
                    myPost.Id = documento.id
                    myPost.Autor = publicaciones.Autor
                    myPost.Categoria = publicaciones.Categoria
                    myPost.Descripcion = publicaciones.Descripcion
                    myPost.Rate = publicaciones.Rate
                    myPost.Titulo = publicaciones.Titulo
                    postList.add(myPost)
                }
                Toast.makeText(context, "Hecho.", Toast.LENGTH_SHORT).show()
                val adapter = PostAdapter(postList)
                recyclerView.adapter = adapter
            }
        }
        val icRuido: ImageButton = view.findViewById(R.id.ic_ruido)
        icRuido.setOnClickListener {
            Toast.makeText(context, "Filtrando, por favor espere.", Toast.LENGTH_SHORT).show()
            db.collection("publicaciones").whereEqualTo("Categoria", "Ruido").addSnapshotListener { snapshot, error ->
                if (error != null){
                    Log.e("error","${error.message}")
                    return@addSnapshotListener
                }
                postList.clear()
                for (documento in snapshot!!) {
                    val publicaciones = documento.toObject(Post::class.java)
                    val myPost = Post()
                    myPost.Id = documento.id
                    myPost.Autor = publicaciones.Autor
                    myPost.Categoria = publicaciones.Categoria
                    myPost.Descripcion = publicaciones.Descripcion
                    myPost.Rate = publicaciones.Rate
                    myPost.Titulo = publicaciones.Titulo
                    postList.add(myPost)
                }
                Toast.makeText(context, "Hecho.", Toast.LENGTH_SHORT).show()
                val adapter = PostAdapter(postList)
                recyclerView.adapter = adapter
            }
        }
        val icBasuras: ImageButton = view.findViewById(R.id.ic_basura)
        icBasuras.setOnClickListener {
            alerta("Integración de mapas de basura en desarrollo SenaSoft SENA 2020 (filtrando por categoría basura...)")
            Toast.makeText(context, "Filtrando, por favor espere.", Toast.LENGTH_SHORT).show()
            db.collection("publicaciones").whereEqualTo("Categoria", "Basuras").addSnapshotListener { snapshot, error ->
                if (error != null){
                    Log.e("error","${error.message}")
                    return@addSnapshotListener
                }
                postList.clear()
                for (documento in snapshot!!) {
                    val publicaciones = documento.toObject(Post::class.java)
                    val myPost = Post()
                    myPost.Id = documento.id
                    myPost.Autor = publicaciones.Autor
                    myPost.Categoria = publicaciones.Categoria
                    myPost.Descripcion = publicaciones.Descripcion
                    myPost.Rate = publicaciones.Rate
                    myPost.Titulo = publicaciones.Titulo
                    postList.add(myPost)
                }
                Toast.makeText(context, "Hecho.", Toast.LENGTH_SHORT).show()
                val adapter = PostAdapter(postList)
                recyclerView.adapter = adapter
            }
        }
        return view
    }

    private fun alerta(mensaje: String) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Un momento")
        builder.setMessage(mensaje)
        builder.setPositiveButton("Ok", null)
        val dialogo: AlertDialog = builder.create()
        dialogo.show()
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