package senasoft2020.buhmed.ui.mispublicaciones

import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.firestore.FirebaseFirestore
import com.huawei.hms.support.hwid.result.AuthHuaweiId
import kotlinx.android.synthetic.main.nav_header_main.*
import senasoft2020.buhmed.Post
import senasoft2020.buhmed.PostAdapter
import senasoft2020.buhmed.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MisPublicacionesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MisPublicacionesFragment : Fragment() {
    val db = FirebaseFirestore.getInstance()
    private val USUARIO = "usuario"
    val PROVEEDOR = "proveedor"
    val CORREO = "correo"
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

    fun selector(p: Post): Int = p.Rate

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_mis_publicaciones, container, false)
        val postList = ArrayList<Post>()
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerViewMisPublicaciones)
        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        db.collection("publicaciones").addSnapshotListener {snapshot, error ->
            if (error != null) {
                Log.e("error", "${error.message}")
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
                // Parce aca antes de agregarlo al postlist hay que buscar el nombre de la cuenta que esta logeada
                //if (nombre de la personas.equals(myPost.Author)) { Aca se comprueba si son iguales los nombres de la cuenta y del autor de la publicacion
                    postList.add(myPost) // Si son iguales se agrega el post al array
                //}
            }
            postList.sortByDescending({selector(it)})
            val adapter = PostAdapter(postList)
            recyclerView.adapter = adapter
        }
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MisPublicacionesFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MisPublicacionesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}