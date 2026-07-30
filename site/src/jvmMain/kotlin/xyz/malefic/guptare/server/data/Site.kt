package xyz.malefic.guptare.server.data

import xyz.malefic.guptare.model.SiteInfo
import xyz.malefic.guptare.server.util.file

var siteInfo by file(
    "site-info.json",
    SiteInfo(
        siteName = "Gupta Realty",
        agentName = "Ruchika Gupta",
        agentLicense = "DRE #02161384",
        agentPhone = "714-767-5752",
        agentEmail = "ruchikagupta@firstteam.com",
        agentAddress = "Orange County, CA",
        brokerageName = "First Team Real Estate",
        brokerageLicense = "[First Team's DRE #]",
        footerDescription = "A real estate experience defined by sophistication, expertise, and personalized service.",
        logoUrl = "/Logo.jpg",
        affiliationLogos =
            listOf(
                "https://media-production.lp-cdn.com/cdn-cgi/image/format=auto,quality=85,fit=scale-down,width=960/https://media-production.lp-cdn.com/media/7d3ff192-7c1e-4f27-ad8c-1b946291eb0a",
                "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fwww.pngkey.com%2Fpng%2Ffull%2F754-7541936_luxury-portfolio-logo-hd.png&f=1&nofb=1&ipt=4fa5eacd254975e773a2a0c3e10e84a0b15d15d70e67419ac8cbd31dd8b6cca2",
            ),
        tiktokUrl = "https://tiktok.com",
        instagramUrl = "https://instagram.com",
        linkedinUrl = "https://linkedin.com",
        disclaimerText = "Equal Housing Opportunity. All information provided is deemed reliable but is not guaranteed and should be independently verified. This is not intended as legal, tax, or financial advice. If you are currently working with another real estate agent under a signed representation agreement, this is not a solicitation of that business relationship.",
        disclaimerLogo = "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fwww.pngmart.com%2Ffiles%2F23%2FEqual-Housing-Opportunity-Logo-PNG-Photo.png&f=1&nofb=1&ipt=fa07ac7c1015e87b5ce8f498a6e3cb5901cc8b26b0e50ada76f04175f5799c29",
    ),
)
