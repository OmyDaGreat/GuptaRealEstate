package xyz.malefic.guptare.server.data

import xyz.malefic.guptare.model.AboutHomeInfo
import xyz.malefic.guptare.model.HelpBoxHomeInfo
import xyz.malefic.guptare.model.HelpHomeInfo
import xyz.malefic.guptare.model.HeroHomeInfo
import xyz.malefic.guptare.model.HomeInfo
import xyz.malefic.guptare.model.SocialHomeInfo
import xyz.malefic.guptare.model.Testimonial
import xyz.malefic.guptare.server.util.file

var homeInfo by file(
    "home-info.json",
    HomeInfo(
        hero =
            HeroHomeInfo(
                title = "Results You'll Love, Without the Guesswork",
                subtitle = "I believe that achieving great results shouldn't come with a side of overwhelm. I’m here to streamline the entire process, giving you total clarity and confidence from day one.",
                image = "/Logo.jpg",
            ),
        stats =
            listOf(
                "Backed by #1 Independent Brokerage in CA",
                "106,000+ clients served broker-wide",
                "61+ office locations expanding across the US",
            ),
        statsNotice = "Brokerage stats provided by FTRE based on 2025 year-end data.",
        help =
            HelpHomeInfo(
                title = "How Can I Help?",
                description = "Whether you are a seller, buyer, or both, I'm here to guide you along every step of the process.",
                boxes =
                    listOf(
                        HelpBoxHomeInfo(
                            image = "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fwww.lgo.org.uk%2Fassets%2Finline%2F5856%2FWEB-Man-with-clipboard-knocking-on-door.jpg&f=1&nofb=1&ipt=f942d90fdfabc624fa58d7b327915b35e9ba626fcfaeaa053b0ea3a2efa76ecd",
                            title = "Sellers",
                            description = "Tailored strategies to maximize your return.",
                        ),
                        HelpBoxHomeInfo(
                            image = "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fwww.lgo.org.uk%2Fassets%2Finline%2F5856%2FWEB-Man-with-clipboard-knocking-on-door.jpg&f=1&nofb=1&ipt=f942d90fdfabc624fa58d7b327915b35e9ba626fcfaeaa053b0ea3a2efa76ecd",
                            title = "Buyers",
                            description = "Achieve homeownership—minus the guesswork.",
                        ),
                    ),
            ),
        about =
            AboutHomeInfo(
                title = "Hi! I'm Ruchika",
                description =
                    """
                    I help buyers, and sellers navigate the OC real estate market with confidence — and I’ve been licensed since January 2022.
                    Originally from India, I’ve called OC home for the past 20 years and have lived in Anaheim since then.

                    I’m known for being personable, organized, and honest — but also a strong advocate when it matters most.
                    Outside of real estate, I’m a big traveler. When I’m not working, you can usually find me playing board games with my kids or finding a new coffee shop somewhere.
                    """.trimIndent(),
                image = "https://lh3.googleusercontent.com/aida-public/AB6AXuCuwNDb-CwDlopOQd4M9z3qBsg47Jva-z3IKYTWAqKhXGqgBv2NtxGRCt-jSRpohSDwMsX40mGSIGNOz1apgYvVFiwjYWU-Hr9gDe9tl3LB2AtgcF9HBpYMqEc4hgpCT-QjcjVm9ziJAGwY14iXUG09Izkj-tWX-_1ms4BS2xhq1Lf7ZXLMJL9tpGfKAdYRfbEQb9HgLYxMpq20gtvpZPknpotYaCYkfxyGkojJSeOyL2LaDJEqrdnx7qKd-slF0Ub2NRLljwbqyEc",
            ),
        insta =
            SocialHomeInfo(
                title = "The Unfiltered Version",
                description = "No perfectly staged content here — just home tours, market truths, and the real stories behind every OC deal. Come hang out on Instagram, where it's a lot more fun than a regular open house.",
                followLink = "https://www.instagram.com/ruchika.realtor/",
                posts = listOf("https://www.instagram.com/p/DR49lRfAfe2/embed/", "https://www.instagram.com/p/DaS1JrPv02w/embed/"),
            ),
        youtube =
            SocialHomeInfo(
                title = "Learn Before You Leap",
                description = "Buying or selling a home comes with a lot of decisions — and a lot of ways to get it wrong. On my YouTube channel, I break down what actually matters: how to avoid costly pitfalls, what smart buyers and sellers do differently, and everything I wish more people knew before they signed on the dotted line.",
                followLink = "https://www.youtube.com/channel/UCbPMvIhONGrwsFiFZmu_sgg",
                posts =
                    listOf(
                        "https://youtube.com/embed/rgIC0NPFwyA?si=rapKvSrJKQEy0hDV?rel=0",
                        "https://youtube.com/embed/xeqV4rADEEM?si=xZ3v5wqfptIG8jy1?rel=0",
                    ),
            ),
        testimonial =
            Testimonial(
                author = "Ruchika",
                quote = "Wow wowow owowow this is a rly long review that says a lot of good things and taht i hope you change rly quickly pls",
                imageSrc = "/Logo.jpg",
            ),
    ),
)
