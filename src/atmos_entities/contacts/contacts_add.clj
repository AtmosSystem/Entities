(in-ns 'atmos-entities.core)

(defadd-entity :contacts [contact] #(insert contacts (values %)))