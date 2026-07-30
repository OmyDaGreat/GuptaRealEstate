package xyz.malefic.guptare.client.pages.admin

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import co.touchlab.kermit.Logger
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.overflow
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.data.add
import com.varabyte.kobweb.core.init.InitRoute
import com.varabyte.kobweb.core.init.InitRouteContext
import com.varabyte.kobweb.silk.components.icons.ms.MsDelete
import com.varabyte.kobweb.silk.components.icons.ms.MsIcon
import com.varabyte.kobweb.silk.components.icons.ms.MsPersonAdd
import com.varabyte.kobweb.silk.style.toModifier
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.H2
import org.jetbrains.compose.web.dom.H3
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text
import xyz.malefic.guptare.client.api.getWebinar
import xyz.malefic.guptare.client.api.getWebinarRegistrations
import xyz.malefic.guptare.client.api.getWebinarReviews
import xyz.malefic.guptare.client.api.getWebinarTips
import xyz.malefic.guptare.client.api.postWebinar
import xyz.malefic.guptare.client.api.postWebinarReviews
import xyz.malefic.guptare.client.api.postWebinarTips
import xyz.malefic.guptare.client.components.AdminField
import xyz.malefic.guptare.client.components.AdminTextArea
import xyz.malefic.guptare.client.components.DateTimeSelector
import xyz.malefic.guptare.client.components.Loading
import xyz.malefic.guptare.client.components.MistakeCard
import xyz.malefic.guptare.client.components.layouts.AdminLayoutData
import xyz.malefic.guptare.client.components.layouts.AdminLayoutScope
import xyz.malefic.guptare.client.components.layouts.AdminPage
import xyz.malefic.guptare.client.styles.AppColors
import xyz.malefic.guptare.client.styles.AppRadius
import xyz.malefic.guptare.client.styles.AppSpacing
import xyz.malefic.guptare.client.styles.ContentCardStyle
import xyz.malefic.guptare.client.styles.HeadlineMdStyle
import xyz.malefic.guptare.client.styles.HeadlineSmStyle
import xyz.malefic.guptare.client.styles.LabelMdStyle
import xyz.malefic.guptare.client.styles.LabelSmStyle
import xyz.malefic.guptare.client.styles.PrimaryButtonStyle
import xyz.malefic.guptare.client.styles.SecondaryButtonStyle
import xyz.malefic.guptare.client.styles.TertiaryButtonStyle
import xyz.malefic.guptare.model.Registration
import xyz.malefic.guptare.model.Webinar
import xyz.malefic.guptare.model.WebinarReview
import xyz.malefic.guptare.model.WebinarTip
import xyz.malefic.guptare.model.WebinarTipsSection

@InitRoute
fun initWebinarPage(ctx: InitRouteContext) {
    ctx.data.add(
        AdminLayoutData(
            AdminPage.WEBINAR,
        ),
    )
}

@Page
@Composable
fun AdminLayoutScope.WebinarPage() {
    val scope = rememberCoroutineScope()
    var webinar by remember { mutableStateOf<Webinar?>(null) }
    var tipsSection by remember { mutableStateOf<WebinarTipsSection?>(null) }
    var reviews by remember { mutableStateOf<List<WebinarReview>?>(null) }
    var registrations by remember { mutableStateOf<List<Registration>?>(null) }
    var message by remember { mutableStateOf<Pair<String, Boolean>?>(null) }

    LaunchedEffect(Unit) {
        val webinarDeferred =
            async {
                getWebinar()
            }
        val tipsSectionDeferred =
            async {
                getWebinarTips()
            }
        val reviewsDeferred =
            async {
                getWebinarReviews()
            }
        val registrationsDeferred =
            async {
                getWebinarRegistrations(token)
            }

        webinar = webinarDeferred.await()
        tipsSection = tipsSectionDeferred.await()
        reviews = reviewsDeferred.await()
        registrations = registrationsDeferred.await()
    }

    Column(Modifier.fillMaxSize().overflow(Overflow.Auto).padding(AppSpacing.S4)) {
        H2(
            HeadlineMdStyle
                .toModifier()
                .margin(
                    bottom = AppSpacing.S4,
                ).toAttrs(),
        ) {
            Text("Webinar Management")
        }

        if (message != null) {
            P(
                Modifier
                    .color(
                        if (message!!.second) AppColors.OnPrimaryContainer else AppColors.Error,
                    ).backgroundColor(
                        if (message!!.second) AppColors.PrimaryContainer else AppColors.ErrorContainer,
                    ).padding(AppSpacing.S2)
                    .borderRadius(AppRadius.Default)
                    .margin(bottom = AppSpacing.S3)
                    .toAttrs(),
            ) {
                Text(message!!.first)
            }
        }

        Column(
            ContentCardStyle.toModifier().fillMaxWidth().margin(
                bottom = AppSpacing.S4,
            ),
        ) {
            Loading(webinar) {
                H2(
                    HeadlineSmStyle
                        .toModifier()
                        .margin(bottom = AppSpacing.S3)
                        .toAttrs(),
                ) {
                    Text("Main Webinar Info")
                }
                AdminField("Header", header) {
                    webinar = webinar?.copy(header = it)
                }
                AdminField("Title", title) {
                    webinar = webinar?.copy(title = it)
                }
                AdminTextArea("Description", description) {
                    webinar = webinar?.copy(description = it)
                }
                AdminField("Image URL", imageSrc) {
                    webinar = webinar?.copy(imageSrc = it)
                }
                DateTimeSelector("Date & Time", instant) {
                    webinar = webinar?.copy(instant = it)
                }

                Button(
                    PrimaryButtonStyle
                        .toModifier()
                        .margin(top = AppSpacing.S3)
                        .toAttrs {
                            onClick {
                                scope.launch {
                                    message =
                                        try {
                                            postWebinar(token, webinar!!)
                                            "Webinar updated successfully" to true
                                        } catch (e: Exception) {
                                            Logger.e(e, "Webinar") { "Failed to update webinar" }
                                            "Failed to update webinar" to false
                                        }
                                }
                            }
                        },
                ) {
                    Text("Save Webinar Info")
                }
            }
        }

        Column(
            ContentCardStyle.toModifier().fillMaxWidth().margin(
                bottom = AppSpacing.S4,
            ),
        ) {
            Loading(registrations) {
                H2(
                    HeadlineSmStyle
                        .toModifier()
                        .margin(bottom = AppSpacing.S3)
                        .toAttrs(),
                ) {
                    Text("Current Registrations")
                }
                if (this.isEmpty()) {
                    P(
                        LabelMdStyle
                            .toModifier()
                            .toAttrs(),
                    ) {
                        Text("No registrations accessible.")
                    }
                } else {
                    this.forEach { registration ->
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .padding(AppSpacing.S2)
                                .border(
                                    1.px,
                                    LineStyle.Solid,
                                    AppColors.OutlineVariant,
                                ).borderRadius(AppRadius.Md)
                                .margin(bottom = AppSpacing.S2)
                                .gap(AppSpacing.S2),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Column(Modifier.weight(1f)) {
                                H3(
                                    LabelMdStyle
                                        .toModifier()
                                        .toAttrs(),
                                ) {
                                    Text("${registration.firstName} ${registration.lastName}")
                                }
                                P(
                                    LabelSmStyle
                                        .toModifier()
                                        .color(AppColors.OnSurfaceVariant)
                                        .toAttrs(),
                                ) {
                                    Text(registration.email)
                                }
                            }
                            P(
                                LabelSmStyle
                                    .toModifier()
                                    .toAttrs(),
                            ) {
                                Text(
                                    registration.phone,
                                )
                            }
                        }
                    }
                }
            }
        }

        Column(
            ContentCardStyle.toModifier().fillMaxWidth().margin(
                bottom = AppSpacing.S4,
            ),
        ) {
            Loading(tipsSection) {
                H2(
                    HeadlineSmStyle
                        .toModifier()
                        .margin(bottom = AppSpacing.S3)
                        .toAttrs(),
                ) {
                    Text("Tips Section (Exactly 3)")
                }

                AdminField("Section Header", header) {
                    tipsSection = tipsSection?.copy(header = it)
                }

                Row(
                    Modifier
                        .fillMaxWidth()
                        .gap(AppSpacing.S2)
                        .margin(bottom = AppSpacing.S4),
                ) {
                    (0..2).forEach { index ->
                        val tip = tips.getOrNull(index) ?: WebinarTip("help", "New Tip", "Description...")
                        Column(
                            Modifier
                                .weight(1f)
                                .padding(AppSpacing.S2)
                                .border(
                                    1.px,
                                    LineStyle.Solid,
                                    AppColors.OutlineVariant,
                                ).borderRadius(AppRadius.Md),
                        ) {
                            H2(
                                LabelMdStyle
                                    .toModifier()
                                    .margin(bottom = AppSpacing.S2)
                                    .toAttrs(),
                            ) {
                                Text("Tip #${index + 1}")
                            }
                            AdminField(
                                "Icon",
                                tip.icon,
                                "https://fonts.google.com/icons",
                            ) {
                                val newList = tips.toMutableList()
                                while (newList.size <= index) {
                                    newList.add(WebinarTip("help", "", ""))
                                }
                                newList[index] = tip.copy(icon = it)
                                tipsSection = tipsSection?.copy(tips = newList)
                            }
                            AdminField("Title", tip.title) {
                                val newList = tips.toMutableList()
                                while (newList.size <= index) {
                                    newList.add(WebinarTip("help", "", ""))
                                }
                                newList[index] = tip.copy(title = it)
                                tipsSection = tipsSection?.copy(tips = newList)
                            }
                            AdminTextArea("Description", tip.description) {
                                val newList = tips.toMutableList()
                                while (newList.size <= index) {
                                    newList.add(WebinarTip("help", "", ""))
                                }
                                newList[index] = tip.copy(description = it)
                                tipsSection = tipsSection?.copy(tips = newList)
                            }
                        }
                    }
                }

                H2(
                    HeadlineSmStyle
                        .toModifier()
                        .margin(bottom = AppSpacing.S3)
                        .toAttrs(),
                ) {
                    Text("Preview")
                }
                Row(
                    Modifier
                        .fillMaxWidth()
                        .gap(AppSpacing.S2)
                        .padding(AppSpacing.S3)
                        .backgroundColor(AppColors.SurfaceLow)
                        .borderRadius(AppRadius.Xl),
                ) {
                    tips.take(3).forEach { tip ->
                        Box(Modifier.weight(1f)) {
                            MistakeCard(
                                tip.title,
                                tip.description,
                            ) { MsIcon(tip.icon, it) }
                        }
                    }
                }

                Button(
                    PrimaryButtonStyle
                        .toModifier()
                        .margin(top = AppSpacing.S3)
                        .toAttrs {
                            onClick {
                                scope.launch {
                                    message =
                                        try {
                                            val finalTips = tipsSection!!.tips.take(3).toMutableList()
                                            while (finalTips.size < 3) {
                                                finalTips.add(WebinarTip("help", "New Tip", "Description..."))
                                            }
                                            val section = tipsSection!!.copy(tips = finalTips)
                                            postWebinarTips(token, section)
                                            "Tips updated successfully" to true
                                        } catch (e: Exception) {
                                            Logger.e(e, "Webinar") { "Failed to update tips" }
                                            "Failed to update tips" to false
                                        }
                                }
                            }
                        },
                ) {
                    Text("Save Tips")
                }
            }
        }

        Column(
            ContentCardStyle.toModifier().fillMaxWidth().margin(
                bottom = AppSpacing.S4,
            ),
        ) {
            Loading(reviews) {
                H2(
                    HeadlineSmStyle
                        .toModifier()
                        .margin(bottom = AppSpacing.S3)
                        .toAttrs(),
                ) {
                    Text("Webinar Reviews")
                }

                if (this.isEmpty()) {
                    P(
                        LabelMdStyle
                            .toModifier()
                            .toAttrs(),
                    ) {
                        Text("No reviews yet.")
                    }
                } else {
                    this.forEachIndexed { index, review ->
                        Column(
                            Modifier
                                .fillMaxWidth()
                                .padding(AppSpacing.S2)
                                .border(
                                    1.px,
                                    LineStyle.Dashed,
                                    AppColors.OutlineVariant,
                                ).borderRadius(AppRadius.Md)
                                .margin(bottom = AppSpacing.S2),
                        ) {
                            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                                H2(
                                    LabelMdStyle
                                        .toModifier()
                                        .toAttrs(),
                                ) {
                                    Text("Review #${index + 1}")
                                }
                                Box(Modifier.weight(1))
                                Button(
                                    TertiaryButtonStyle
                                        .toModifier()
                                        .padding(AppSpacing.S1)
                                        .toAttrs {
                                            onClick {
                                                reviews = reviews!!.toMutableList().apply { removeAt(index) }
                                            }
                                        },
                                ) {
                                    MsDelete(Modifier.margin(right = 4.px))
                                    Text("Remove")
                                }
                            }
                            AdminField("Reviewer", review.reviewer) {
                                val newList = reviews!!.toMutableList()
                                newList[index] = review.copy(reviewer = it)
                                reviews = newList
                            }
                            AdminField(
                                "Reviewer Description",
                                review.reviewerDescription,
                            ) {
                                val newList = reviews!!.toMutableList()
                                newList[index] = review.copy(reviewerDescription = it)
                                reviews = newList
                            }
                            AdminTextArea("Review", review.review) {
                                val newList = reviews!!.toMutableList()
                                newList[index] = review.copy(review = it)
                                reviews = newList
                            }
                        }
                    }
                }

                Row(
                    Modifier
                        .gap(AppSpacing.S2)
                        .margin(top = AppSpacing.S3),
                ) {
                    Button(
                        SecondaryButtonStyle
                            .toModifier()
                            .toAttrs {
                                onClick {
                                    reviews = reviews!! + WebinarReview("Name", "Description", "Review...")
                                }
                            },
                    ) {
                        MsPersonAdd(Modifier.margin(right = 4.px))
                        Text("Add Review")
                    }

                    Button(
                        PrimaryButtonStyle
                            .toModifier()
                            .toAttrs {
                                onClick {
                                    scope.launch {
                                        message =
                                            try {
                                                postWebinarReviews(token, reviews!!)
                                                "Reviews updated successfully" to true
                                            } catch (e: Exception) {
                                                Logger.e(e, "Webinar") { "Failed to update reviews" }
                                                "Failed to update reviews" to false
                                            }
                                    }
                                }
                            },
                    ) {
                        Text("Save Reviews")
                    }
                }
            }
        }
    }
}
