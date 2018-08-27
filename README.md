# BitfinexRestApi

Android implementation of Bitfinex Rest Public Endpoint

## Getting Started

Download the repo, on Android Studio select File -> Import Module -> Choose the downloaded module -> Finish. Ope settings.gradle file add ':bitfinexapi' to include line. It shouşd look like this:
```
include ':app', ':bitfinexapi'
```
Now open app's build.gradle file and add implementation project(':bitfinexapi') to dependencies.
```
dependencies {
    ...
    implementation project(':bitfinexapi')
}
```

### Bitfinex Api v1

Api v1 methods are listed in com.duvarapps.bitfinexapi.v1.api.BitfinexApi interface. **All Pull methods are sync and get methods are async.**
```
BitfinexApi bfx1 = BitfinexFactory.createClient();
try {
  Result<Ticker> result_etcusd = bfx1.pullTicker("etcusd");
  if (result_etcusd.responseCode == 200) {
      Log.d("BFX", String.valueOf(result_etcusd.body.getLastPrice()));
  }
}
catch (IOException e) {
    e.printStackTrace();
}
```
### Bitfinex Api v2

Api v2 methods are listed in com.duvarapps.bitfinexapi.v2.api.BitfinexApi2 interface.
```
BitfinexApi2 bfx2 = BitfinexFactory2.createClient();
bfx2.getTradingPair("tBTCUSD", new BitfinexApiCallback<TradingPair>() {
    @Override
    public void onResponse(TradingPair tradingPair, ApiResponse response) {
        if (response.code() == 200) {
            tv1.setText(String.valueOf(tradingPair.getLastPrice()));
        }
    }

    @Override
    public void onError(Throwable t) {
        t.printStackTrace();
    }
});
```
## IMPORTANT

Do not trust this library for trading.

## License

This project is licensed under the MIT License 

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

