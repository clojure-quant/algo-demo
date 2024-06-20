(ns algodemo.error.algo
  (:require
   [taoensso.timbre :refer [info warn error]]))

(defn error-algo [_env opts bar-ds]
  (warn "running error-algo: trailing-n: " (:trailing-n opts) " asset: " (:asset opts))
  (throw (Exception. "runtime algo-calc exception!")))

