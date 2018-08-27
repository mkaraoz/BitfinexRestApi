package com.duvarapps.bitfinex;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.duvarapps.bitfinexapi.core.ApiResponse;
import com.duvarapps.bitfinexapi.core.BitfinexApiCallback;
import com.duvarapps.bitfinexapi.core.Result;
import com.duvarapps.bitfinexapi.v1.api.BitfinexApi;
import com.duvarapps.bitfinexapi.v1.api.BitfinexFactory;
import com.duvarapps.bitfinexapi.v1.pojo.Ticker;
import com.duvarapps.bitfinexapi.v2.api.BitfinexApi2;
import com.duvarapps.bitfinexapi.v2.api.BitfinexFactory2;
import com.duvarapps.bitfinexapi.v2.pojo.FundingCurrency;
import com.duvarapps.bitfinexapi.v2.pojo.TradingPair;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity
{
    private BitfinexApi bfx1;
    private BitfinexApi2 bfx2;

    @Override
    protected void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bfx1 = BitfinexFactory.createClient();
        bfx2 = BitfinexFactory2.createClient();

        final TextView tv1 = findViewById(R.id.left1);
        final Button b = findViewById(R.id.button);

        b.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                bfx2.getTradingPair("tBTCUSD", new BitfinexApiCallback<TradingPair>()
                {
                    @Override
                    public void onResponse(TradingPair tradingPair, ApiResponse response)
                    {
                        if (response.code() == 200)
                        {
                            tv1.setText(String.valueOf(tradingPair.getLastPrice()));
                        }
                    }

                    @Override
                    public void onError(Throwable t)
                    {
                        t.printStackTrace();
                    }
                });

                ExecutorService executor = Executors.newFixedThreadPool(2);
                executor.submit(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        try
                        {
                            Result<FundingCurrency> result_fUSD = bfx2.pullFundingCurrency("fUSD");
                            if (result_fUSD.responseCode == 200)
                            {
                                Log.d("BFX", String.valueOf(result_fUSD.body.getLastPrice()));
                            }
                        }
                        catch (IOException e)
                        {
                            e.printStackTrace();
                        }
                    }
                });
                executor.submit(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        try
                        {
                            Result<Ticker> result_etcusd = bfx1.pullTicker("etcusd");
                            if (result_etcusd.responseCode == 200)
                            {
                                Log.d("BFX", String.valueOf(result_etcusd.body.getLastPrice()));
                            }
                        }
                        catch (IOException e)
                        {
                            e.printStackTrace();
                        }
                    }
                });
                executor.shutdown();
            }
        });
    }

}
