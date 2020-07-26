package ru.spb.alevall.mailservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@Table(name = "mail_lists_logs")
@AllArgsConstructor
public class MailListLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = MailList.class, fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinColumn(name = "mail_list_id")
    private MailList mailList;

    @Column(nullable = false, unique = true)
    private String url;

    @Column(nullable = false)
    private LocalDateTime transferDate;
}
