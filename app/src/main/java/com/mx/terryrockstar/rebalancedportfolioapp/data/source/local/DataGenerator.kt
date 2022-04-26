package com.mx.terryrockstar.rebalancedportfolioapp.data.source.local

import com.mx.terryrockstar.rebalancedportfolioapp.data.Group

class DataGenerator {

    companion object {
        fun getGroup(): Group {
            return Group(0, "Otros", 0.0f)
        }
    }
}