package xyz.malefic.guptare.server.data

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import xyz.malefic.guptare.model.Registration
import xyz.malefic.guptare.model.Webinar
import xyz.malefic.guptare.model.WebinarReview
import xyz.malefic.guptare.model.WebinarTip
import xyz.malefic.guptare.model.WebinarTipsSection
import xyz.malefic.guptare.server.util.file

var registrations by file("registrations.json", HashMap<String, ArrayList<Registration>>())
var webinarTips by file(
    "webinar-tips.json",
    WebinarTipsSection(
        "Common First Time Home Buyer Mistakes",
        listOf(
            WebinarTip(
                "account_balance_wallet",
                "Skipping Pre-Approval",
                "Don't start the hunt without knowing your budget. Pre-approval gives you leverage and clarity in a competitive market.",
            ),
            WebinarTip(
                "home_work",
                "Ignoring Extra Costs",
                "Beyond the mortgage, consider closing costs, inspections, and insurance. We help you map out the full financial picture.",
            ),
            WebinarTip(
                "psychology",
                "Emotional Over-Investing",
                "It's easy to fall for a aesthetic and ignore structural red flags. We keep you grounded in data and long-term value.",
            ),
        ),
    ),
)
var webinarReviews by file(
    "webinar-reviews.json",
    listOf(
        WebinarReview(
            "Mark & Sarah Thompson",
            "New Homeowners, Irvine",
            "Ruchika's webinar was the turning point for us. She simplified the escrow process and gave us the confidence to put in our first offer. We closed last month!",
        ),
        WebinarReview(
            "idk some reviewer",
            "Retired, Anaheim",
            "Some rly rly long review ahhhgiweajifgjwalfjwal",
        ),
    ),
)
var currentWebinar by file(
    "current-webinar.json",
    Webinar(
        "FREE LIVE EDUCATIONAL SERIES",
        "Mastering the Market: A Guide for First-Time Buyers",
        "Join Ruchika Gupta for an exclusive deep dive into the 2026 real estate landscape. Learn how to navigate interest rates and find your dream home with confidence.",
        "I consent to receive exclusive market updates from Ruchika Gupta.",
        LocalDateTime(2026, 6, 23, 4, 0).toInstant(TimeZone.currentSystemDefault()),
        "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fstatic.vecteezy.com%2Fsystem%2Fresources%2Fpreviews%2F065%2F940%2F783%2Fnon_2x%2Fnew-apartment-keys-exchange-modern-residential-building-luxury-living-space-real-estate-investment-home-purchase-property-sale-free-photo.jpeg&f=1&nofb=1&ipt=ade2933d2fac61b9b5e7553389057e7bc2079893b38b72deb450dc71c3684a0f",
    ),
)
