(ns algodemo.sma-crossover.template
  (:require
   [quanta.trade.backtest :refer [backtest]]
   [quanta.dali.plot :as plot]
   [quanta.bar.env :refer [get-trailing-bars]]
   [algodemo.sma-crossover.algo :refer [sma-crossover-algo]]))

(def crossover-algo
  {:* {:asset "BTCUSDT"}
   :bars  {:calendar [:crypto :h]
           :fn get-trailing-bars
           :bardb :nippy
           :trailing-n 1440}
   :algo {:formula [:bars]
          :fn sma-crossover-algo
          :sma-st 20
          :sma-lt 200}
   :backtest {:formula [:algo]
              :fn backtest
              ; position management
              :entry [:fixed-amount 100000]
              :exit [:loss-percent 2.0
                     :profit-percent 1.0
                     :time 5]}})

(def crossover-options
  [{:type :select
    :path [:* :asset]
    :name "Asset"
    :spec ["BTCUSDT" "ETHUSDT"]}
   {:type :select
    :path [:bars :trailing-n]
    :name "trailing-n"
    :spec [100 300 500 1000 2000 3000 5000 10000]}
   {:type :select
    :path [:algo :sma-st]
    :name "sma-st"
    :spec [10 20 50 100]}
   {:type :select
    :path [:algo :sma-lt]
    :name "sma-lt"
    :spec [100 200 500 1000]}])


(def sma-chart
  {:viz plot/highstock-ds
   :key :algo
   :viz-options {:charts [{:bar {:type :ohlc
                                 :mode :candle}
                           :close :line
                           :sma-lt :line
                           :sma-st :line
                                     ;:signal :flags
                           :signal {:type :flags
                                    :fillColor "blue"
                                    :width 10
                                    :height 10
                                    :v2style {;:long "square"
                                              :long "url(/r/arrow-up.svg)"
                                              true "flags"
                                                        ;:short "circle"
                                              :short "url(/r/arrow-down.svg)"}}}
                          #_{:volume :column}]}})

(def sma-table
  {:viz plot/rtable-ds
   :key :algo
   :viz-options {:columns [{:path :date}
                           {:path :close}
                           {:path :sma-st}
                           {:path :sma-lt}
                           {:path :position}
                           {:path :signal}]}})

(def sma-backtest
  {:key :backtest
   :viz plot/backtest-ui-ds
   :viz-options {}})

(def crossover-template
  {:id :sma-crossover
   :algo crossover-algo
   :options crossover-options
   :chart sma-chart
   :table sma-table
   :backtest sma-backtest})


#_(def eodhd-eod
    {:id :sma-eodhd-eod
     :algo {:type :trailing-bar
            :algo 'algodemo.sma-crossover.algo/sma-crossover-algo
            :calendar [:us :d]
            :asset ""
            :import :eodhd
            :trailing-n 400
            :sma-length-st 20
            :sma-length-lt 200}
     :options [{:type :asset-picker ; custom DYNAMIC UI!!!
                :path :asset
                :name "Asset"}
               {:type :select
                :path :trailing-n
                :name "trailing-n"
                :spec [200 400 600 800 1000 2000 3000 5000 10000]}
               {:type :select
                :path :sma-length-st
                :name "sma-st"
                :spec [10 20 50 100]}
               {:type :select
                :path :sma-length-lt
                :name "sma-lt"
                :spec [100 200 500 1000]}]
     :chart {:viz 'ta.viz.ds.highchart/highstock-render-spec
             :viz-options {:chart {:box :fl}
                           :charts [{:bar :candlestick ; :ohlc ; :line 
                                     :sma-lt :line
                                     :sma-st :line}
                                    #_{:volume :column}]}}})
