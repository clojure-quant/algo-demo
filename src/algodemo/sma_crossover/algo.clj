(ns algodemo.sma-crossover.algo
  (:require
   [tablecloth.api :as tc]
   [quanta.algo.env.bars :refer [get-trailing-bars]]
   [ta.indicator :refer [sma]]
   [ta.indicator.signal :refer [changed-signal-or]]))

(defn- calc-sma-signal [sma-st sma-lt]
  (if (and sma-st sma-lt)
    (cond
      (> sma-st sma-lt) :long
      (< sma-st sma-lt) :short
      :else :hold)
    :hold))

(defn sma-crossover-algo [{:keys [sma-length-st sma-length-lt] :as opts} dt]
  (let [bar-ds (get-trailing-bars opts dt)
        price (:close bar-ds)
        sma-st (sma {:n sma-length-st} price)
        sma-lt (sma {:n sma-length-lt} price)
        position (into [] (map calc-sma-signal sma-st sma-lt))
        signal (changed-signal-or position :hold)]
    (tc/add-columns bar-ds {:sma-st sma-st
                            :sma-lt sma-lt
                            :position position
                            :signal signal})))


