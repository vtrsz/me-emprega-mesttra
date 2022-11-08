package com.mesttra.meemprega.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mesttra.meemprega.entity.Job;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class SearchService {
    @Autowired
    EmailService emailService;

    public Document getHtml(String keyWords) {
        String url = String.format("https://www.linkedin.com/jobs/search?keywords=%s&location=%s&geoId=&trk=homepage-jobseeker_jobs-search-bar_search-submit&position=1&pageNum=0", keyWords.toLowerCase(), "brazil");
        Document document = null;
        try {
            document = Jsoup.connect(url)
                    //.userAgent("Edge")
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.116 Safari/537.36")
                    .get();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return document;
    }

    public List<Element> getJobsList(Document html) {
        Element jobContainer = html.getElementsByClass("jobs-search__results-list").first();
        assert jobContainer != null;
        List<Element> jobsList = jobContainer.getElementsByTag("li");
        return jobsList;
    }

    public ArrayList<Job> getJobInformation(List<Element> jobList){
        ArrayList<Job> jobs = new ArrayList<>();
        String jobTitle, jobCompany, jobLocation, jobLink, jobCompanyLink, jobReleaseTime;
        boolean hasTitle, hasCompany, hasLocation, hasJobLink, hasCompanyLink, hasReleaseTime;
        for ( Element job : jobList) {
            hasTitle = !job.getElementsByClass("base-search-card__title").text().equalsIgnoreCase("");
            hasCompany = !job.getElementsByClass("base-search-card__subtitle").text().equalsIgnoreCase("");
            hasCompanyLink = !job.select("a[href*=/company/]").attr("abs:href").isEmpty();
            hasLocation = !job.getElementsByClass("job-search-card__location").text().equalsIgnoreCase("");
            hasJobLink = !job.select("a[href*=/jobs/view/]").attr("abs:href").isEmpty();
            hasReleaseTime = !job.getElementsByClass("job-search-card__listdate").text().equalsIgnoreCase("");

            if (hasTitle && hasCompany && hasCompanyLink && hasLocation && hasJobLink && hasReleaseTime) {
                jobTitle = job.getElementsByClass("base-search-card__title").get(0).text();
                jobCompany = job.getElementsByClass("base-search-card__subtitle").get(0).text();
                jobCompanyLink = job.select("a[href*=/company/]").get(0).attr("abs:href");
                jobLocation = job.getElementsByClass("job-search-card__location").get(0).text();
                jobLink = job.select("a[href*=/jobs/view/]").get(0).attr("abs:href");
                jobReleaseTime = job.getElementsByClass("job-search-card__listdate").get(0).text();

                Job jobEntity = new Job(jobTitle, jobCompany, jobCompanyLink, jobLocation, jobLink, jobReleaseTime);
                jobs.add(jobEntity);
            }
        }
        return jobs;
    }

    public ArrayList<Job> search(String keyWords, String email) {
        Document html = getHtml(keyWords);
        List<Element> jobsList = getJobsList(html);
        ArrayList<Job> jobs = getJobInformation(jobsList);

        StringBuilder jobsString = new StringBuilder();
        for (Job job : jobs) {
            jobsString.append(job.formatToMail()).append("<br>");
        }

        if (!Objects.equals(email, "null")) {
            emailService.sendEmail(keyWords, email, String.valueOf(jobsString));
        }
        return jobs;
    }

    public ArrayList<Job> searchAndGetApi(String keyWords) throws JsonProcessingException {
        ArrayList<Job> jobs = search(keyWords, "null");
        return jobs;
    }
}