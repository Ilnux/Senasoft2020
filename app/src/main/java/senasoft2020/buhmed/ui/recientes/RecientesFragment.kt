package senasoft2020.buhmed.ui.recientes

import android.graphics.Typeface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_recientes.*
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

        val postList = ArrayList<Post>()
        postList.add(Post("Queja consumo", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc dapibus, ex in eleifend tempor, tortor ipsum dictum ipsum, eu sodales lacus ex at nisl.", 4))
        postList.add(Post("Queja ruido", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc dapibus, ex in eleifend tempor, tortor ipsum dictum ipsum, eu sodales lacus ex at nisl.", -10))
        postList.add(Post("Basuras en el parque ambiental", "Buenas, muchas personas que viven en el barrio La Francia han estado dejando ultimamente sus bolsas de basura en el parque mbiental para ser recogidas", 100))
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