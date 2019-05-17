package eu.europa.ecb.ui.rates

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import android.content.Context
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import android.os.IBinder
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.navigation.fragment.findNavController
import eu.europa.ecb.AppExecutors
import eu.europa.ecb.R
import eu.europa.ecb.binding.FragmentDataBindingComponent
import eu.europa.ecb.databinding.ExchangeRatesFragmentBinding
import eu.europa.ecb.di.Injectable
import eu.europa.ecb.ui.common.CubeItemListAdapter
import eu.europa.ecb.ui.common.ClickCallback
import eu.europa.ecb.util.DateUtils
import eu.europa.ecb.util.autoCleared
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class ExchangeRatesFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var appExecutors: AppExecutors

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<ExchangeRatesFragmentBinding>()

    lateinit var exchangeRatesViewModel: ExchangeRatesViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.exchange_rates_fragment,
                container,
                false,
                dataBindingComponent
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        exchangeRatesViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(ExchangeRatesViewModel::class.java)
        binding.lifecycleOwner = viewLifecycleOwner
        initRecyclerView()

        binding.rateInList.adapter = CubeItemListAdapter(
                dataBindingComponent = dataBindingComponent,
                appExecutors = appExecutors
        ) { cubeItem ->
            exchangeRatesViewModel.setSelectedIn(cubeItem)
            binding.currency = cubeItem.currency
        }
        binding.rateOutList.adapter = CubeItemListAdapter(
                dataBindingComponent = dataBindingComponent,
                appExecutors = appExecutors
        ) { cubeItem ->
            exchangeRatesViewModel.setSelectedOut(cubeItem)
        }

        initAmountInputListener()

        binding.callback = object: ClickCallback {
            override fun click() {
                navController().navigate(ExchangeRatesFragmentDirections.show())
            }
        }

        exchangeRatesViewModel.loadUpdatedTime().observe(viewLifecycleOwner, Observer {
            binding.updated = "Exchange rates day: ${it.rateTime}\nUpdated: ${DateUtils.format(Date(it.updatedTime))}"
        })
    }

    private fun doCalculate() {
        exchangeRatesViewModel.setValue(binding.input.getValue())
        binding.result = exchangeRatesViewModel.result
    }

    private fun initRecyclerView() {
        val result = exchangeRatesViewModel.load()
        binding.searchResult = result
        result.observe(viewLifecycleOwner, Observer { resource ->
            Timber.d("got result $resource")
            (binding.rateInList.adapter as CubeItemListAdapter).submitList(resource.data)
            (binding.rateOutList.adapter as CubeItemListAdapter).submitList(resource.data)
        })
    }

    private fun initAmountInputListener() {
        binding.input.setOnEditorActionListener { view: View, actionId: Int, _: KeyEvent? ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                dismissKeyboard(view.windowToken)
                true
            } else {
                false
            }
        }

        binding.input.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                doCalculate()
            }
        })
    }

    private fun dismissKeyboard(windowToken: IBinder) {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(windowToken, 0)
    }

    /**
     * Created to be able to override in tests
     */
    fun navController() = findNavController()
}
