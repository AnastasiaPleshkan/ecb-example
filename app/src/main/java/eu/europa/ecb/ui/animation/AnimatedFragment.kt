package eu.europa.ecb.ui.animation

import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.airbnb.lottie.TextDelegate
import eu.europa.ecb.AppExecutors
import eu.europa.ecb.R
import eu.europa.ecb.binding.FragmentDataBindingComponent
import eu.europa.ecb.databinding.AnimatedFragmentBinding
import eu.europa.ecb.di.Injectable
import eu.europa.ecb.util.autoCleared
import javax.inject.Inject

class AnimatedFragment : Fragment(), Injectable {

    @Inject
    lateinit var appExecutors: AppExecutors

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<AnimatedFragmentBinding>()
    private var textDelegate: TextDelegate? = null

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.animated_fragment,
                container,
                false,
                dataBindingComponent
        )

        textDelegate = TextDelegate(binding.dynamicText)
        binding.dynamicText.setTextDelegate(textDelegate)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.lifecycleOwner = viewLifecycleOwner
    }

}
