package ru.wolfram.administration

import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component
import ru.wolfram.administration.service.AdministrationService

@Component
class ConsoleRunner(
    private val administrationService: AdministrationService
) : ApplicationRunner {
    override fun run(args: ApplicationArguments) {
        lateinit var title: String
        var pendingAccept = false
        var pendingReject = false
        val yesNo = setOf("yes", "no")
        while (true) {
            val input = readlnOrNull()
            input?.let {
                val response = it.trim().lowercase()
                if (pendingAccept) {
                    if (response in yesNo) {
                        pendingAccept = false
                        when (response) {
                            "yes" -> {
                                administrationService.accept(title)
                            }

                            else -> {

                            }
                        }
                    } else {
                        println("yes or no is expected!")
                        continue
                    }
                }

                if (pendingReject) {
                    if (response in yesNo) {
                        pendingReject = false
                        when (response) {
                            "yes" -> {
                                administrationService.reject(title)
                            }

                            else -> {

                            }
                        }
                    } else {
                        println("yes or no is expected!")
                        continue
                    }
                }

                when (response) {
                    "get" -> {
                        administrationService.getVoteOffer()?.let { vote ->
                            if (vote.isEmpty()) {
                                println("Vote is empty! How is it possible?")
                            } else {
                                title = vote.first().title
                                println("Title: $title")
                                vote.forEachIndexed { index, vote ->
                                    println("${index + 1}: ${vote.variant}")
                                }
                            }
                        }
                    }

                    "accept" -> {
                        pendingAccept = true
                        println("Are you sure you want to accept vote with title: ${title.take(20)}...? [yes/no]")
                    }

                    "reject" -> {
                        pendingReject = true
                        println("Are you sure you want to reject vote with title: ${title.take(20)}...? [yes/no]")
                    }
                }

            }
        }
    }
}