package com.mesttra.meemprega.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
public class Job {
    private String jobTitle, jobCompany, jobCompanyLink, jobLocation, jobLink, jobReleaseTime;

    @ToString.Include
    public String toString() {
        return (""
                + "Título: " + jobTitle + "\n"
                + "Empresa: " + jobCompany + "\n"
                + "Perfil da Empresa: " + jobCompanyLink + "\n"
                + "Local: " + jobLocation + "\n"
                + "Link da vaga: " + jobLink + "\n"
                + jobReleaseTime + "\n"
        );
    }

    public String formatToMail() {
        return (""
                + "Título: " + jobTitle + "<br>"
                + "Empresa: " + jobCompany + "<br>"
                + "Perfil da Empresa: " + jobCompanyLink + "<br>"
                + "Local: " + jobLocation + "<br>"
                + "Link da vaga: " + jobLink + "<br>"
                + jobReleaseTime + "<br>"
        );
    }
}