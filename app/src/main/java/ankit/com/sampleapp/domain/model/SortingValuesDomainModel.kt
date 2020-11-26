package ankit.com.sampleapp.domain.model

data class SortingValuesDomainModel(val averageProductPrice: Int = 0,
                                    val bestMatch: Int = 0,
                                    val distance: Int = 0,
                                    val deliveryCosts: Int = 0,
                                    val popularity: Int = 0,
                                    val newest: Int = 0,
                                    val minCost: Int = 0,
                                    val ratingAverage: Double = 0.0)