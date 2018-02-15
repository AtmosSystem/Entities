(in-ns 'atmos-entities.core)


(defn- add-persist-entity*
  [entity]
  (insert entities
          (values entity)))

(defn- add-entities*
  [entities]
  (if-let [key-inserted (add-persist-entity* entities)]
    (:generated_key key-inserted)
    false))