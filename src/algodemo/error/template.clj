(ns algodemo.error.template)


(def error-compile-algo
  {:id :error/compile-algo
   :algo {:type :trailing-bar
          :calendar [:crypto :m]
          :trailing-n 1440
          :import :bybit
          :asset "BTCUSDT"
          :algo 'algodemo.error.algo-bad/error-algo} ; error in the symbol (-bad is too much)
   :options []
   :chart {:viz 'ta.viz.ds.highchart/highstock-render-spec
           :viz-options {:chart {:box :fl}
                         :charts [{:bar :candlestick}

                                  {:volume :column}]}}})

(def error-runtime-algo
  {:id :error/runtime-algo
   :algo {:type :trailing-bar
          :calendar [:crypto :m]
          :trailing-n 1440
          :import :bybit
          :asset "BTCUSDT"
          :algo 'algodemo.error.algo/error-algo}
   :options []
   :chart {:viz 'ta.viz.ds.highchart/highstock-render-spec
           :viz-options {:chart {:box :fl}
                         :charts [{:bar :candlestick}

                                  {:volume :column}]}}})