package xyz.malefic.guptare.server.data

import kotlinx.datetime.LocalDate
import xyz.malefic.guptare.model.BlogPostResponse
import xyz.malefic.guptare.server.util.file
import kotlin.uuid.Uuid

val blogs by file(
    "blog.json",
    mutableListOf(
        BlogPostResponse(
            Uuid.random(),
            "Market Trends 2026",
            "Navigating the shifting landscape of Cheshire real estate this coming year...",
            """# THIS IS LOTS OF CONTENT YES YES YES YESYES YES ESYES ESE YS E
                |## THIS IS MORE CONTENT!!!
                |### EVEN MORE!!!!!
            """.trimMargin(),
            "https://lh3.googleusercontent.com/aida-public/AB6AXuCPQZL2d06Aa7tXDLhenIb7TMP3AlZsEgxvn6BmxXa4p8CtEwaidUKayGU9xEwH9DNifWfz76cqSg3eEZx3ACe5L_aESgI_5CrB58aCYrdz-AcUD1x3JBhNJ4UpA0kl1RreUGJ4mMwgSu0MvKiqsc_fLmAf_FIVlyC_1aoDZZPR9zDzQTXQNR8luWCnNXDlkAZhOQb8sjoVsZlpE6XwnmKj5Yis3rSucsMp1EIwL6HFctIC9ZMk_cUiYYCeiJweUgEFlFl5F-JItmY",
            listOf("Market Update", "Buying", "Selling"),
            LocalDate(2026, 1, 1),
        ),
        BlogPostResponse(
            Uuid.random(),
            "Home Staging Tips",
            "How to maximize your home's appeal and sale price with minimal investment.",
            """# THIS IS LOTS OF CONTENT YES YES YES YESYES YES ESYES ESE YS E
                |## THIS IS MORE CONTENT!!!
                |### EVEN MORE!!!!!
            """.trimMargin(),
            "https://lh3.googleusercontent.com/aida-public/AB6AXuCtr9pJ0DNY8UIV7gtfBfxpuxgPe9OSLDMPmSEDEQAf2PtKbx9-PUUrzXgdT--9Ks1AHMiKSvH3GjzinqrPPZPpdMhZE2v47cutqbMzo4Y7wISrUUe1fjtevMCY_K0MZx0C5PM6UVqY49XWO48bY14PTzDpX8UtCL4ST6sk6q1an7ZiYPsvh_srX6ORzu82zJkC9oF-nWnmhoRs73s-BIzV3qhvjZjCfAEXeMth6yU114HTF31WvNqDEKo27mFv6_jaWh9gDHFYw74",
            listOf("Staging", "Selling"),
            LocalDate(2026, 1, 15),
        ),
        BlogPostResponse(
            Uuid.random(),
            "Buying vs Renting",
            "Making the right financial decision for your family",
            """# THIS IS LOTS OF CONTENT YES YES YES YESYES YES ESYES ESE YS E
                |## THIS IS MORE CONTENT!!!
                |### EVEN MORE!!!!!
            """.trimMargin(),
            "https://lh3.googleusercontent.com/aida-public/AB6AXuApF3zCv4r3yPfgXw5wgSnGKWm63gO0maISD20iFb-nq_z6A-tzk8KSSKf9slb15DvKCxkXDV2xTmG9xp-lFikYsklaMfCHe4Ptw-XSbOz8wtGAnq4vLQihVym2CkYvaFvT-X4Nx6d6U8sXS8AqpXb4Q2Aog_5_dkJvBzHCnuoFLgyC8Jq-0_5rljMAA2PpXclHbJZxiG81XRVM40HJh--uJp6yLunjybeWbuSicXLGGj24N5XlJnrkVhfR495WNpjz9vzzKdsGBgo",
            listOf("Financial Advice", "Buying"),
            LocalDate(2026, 1, 10),
        ),
    ),
)
