package com.museum.app.documentation.web;

import com.museum.app.documentation.dto.DocumentationDtos.ArtHistoricalReportRequest;
import com.museum.app.documentation.dto.DocumentationDtos.ArtHistoricalReportResponse;
import com.museum.app.documentation.dto.DocumentationDtos.ConditionReportRequest;
import com.museum.app.documentation.dto.DocumentationDtos.ConditionReportResponse;
import com.museum.app.documentation.dto.DocumentationDtos.InsuranceRequest;
import com.museum.app.documentation.dto.DocumentationDtos.InsuranceResponse;
import com.museum.app.documentation.dto.DocumentationDtos.LoanRequest;
import com.museum.app.documentation.dto.DocumentationDtos.LoanResponse;
import com.museum.app.documentation.dto.DocumentationDtos.ObjectDocumentRequest;
import com.museum.app.documentation.dto.DocumentationDtos.ObjectDocumentResponse;
import com.museum.app.documentation.dto.DocumentationDtos.ProvenanceRequest;
import com.museum.app.documentation.dto.DocumentationDtos.ProvenanceResponse;
import com.museum.app.documentation.dto.DocumentationDtos.ResearchReportRequest;
import com.museum.app.documentation.dto.DocumentationDtos.ResearchReportResponse;
import com.museum.app.documentation.dto.DocumentationDtos.RestorationRequest;
import com.museum.app.documentation.dto.DocumentationDtos.RestorationResponse;
import com.museum.app.documentation.service.ObjectDocumentationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/objects/{objectId}")
public class ObjectDocumentationController {

    private final ObjectDocumentationService documentationService;

    public ObjectDocumentationController(ObjectDocumentationService documentationService) {
        this.documentationService = documentationService;
    }

    @GetMapping("/condition-reports")
    public List<ConditionReportResponse> listConditionReports(@PathVariable UUID objectId) {
        return documentationService.listConditionReports(objectId);
    }

    @PostMapping("/condition-reports")
    public ResponseEntity<ConditionReportResponse> createConditionReport(
            @PathVariable UUID objectId,
            @Valid @RequestBody ConditionReportRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(documentationService.createConditionReport(objectId, request));
    }

    @PutMapping("/condition-reports/{reportId}")
    public ConditionReportResponse updateConditionReport(
            @PathVariable UUID objectId,
            @PathVariable UUID reportId,
            @Valid @RequestBody ConditionReportRequest request
    ) {
        return documentationService.updateConditionReport(objectId, reportId, request);
    }

    @DeleteMapping("/condition-reports/{reportId}")
    public ResponseEntity<Void> deleteConditionReport(
            @PathVariable UUID objectId,
            @PathVariable UUID reportId
    ) {
        documentationService.deleteConditionReport(objectId, reportId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/provenance")
    public List<ProvenanceResponse> listProvenance(@PathVariable UUID objectId) {
        return documentationService.listProvenance(objectId);
    }

    @PostMapping("/provenance")
    public ResponseEntity<ProvenanceResponse> createProvenance(
            @PathVariable UUID objectId,
            @Valid @RequestBody ProvenanceRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(documentationService.createProvenance(objectId, request));
    }

    @PutMapping("/provenance/{entryId}")
    public ProvenanceResponse updateProvenance(
            @PathVariable UUID objectId,
            @PathVariable UUID entryId,
            @Valid @RequestBody ProvenanceRequest request
    ) {
        return documentationService.updateProvenance(objectId, entryId, request);
    }

    @DeleteMapping("/provenance/{entryId}")
    public ResponseEntity<Void> deleteProvenance(@PathVariable UUID objectId, @PathVariable UUID entryId) {
        documentationService.deleteProvenance(objectId, entryId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/insurance")
    public List<InsuranceResponse> listInsurance(@PathVariable UUID objectId) {
        return documentationService.listInsurance(objectId);
    }

    @PostMapping("/insurance")
    public ResponseEntity<InsuranceResponse> createInsurance(
            @PathVariable UUID objectId,
            @Valid @RequestBody InsuranceRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(documentationService.createInsurance(objectId, request));
    }

    @PutMapping("/insurance/{entryId}")
    public InsuranceResponse updateInsurance(
            @PathVariable UUID objectId,
            @PathVariable UUID entryId,
            @Valid @RequestBody InsuranceRequest request
    ) {
        return documentationService.updateInsurance(objectId, entryId, request);
    }

    @DeleteMapping("/insurance/{entryId}")
    public ResponseEntity<Void> deleteInsurance(@PathVariable UUID objectId, @PathVariable UUID entryId) {
        documentationService.deleteInsurance(objectId, entryId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/restorations")
    public List<RestorationResponse> listRestorations(@PathVariable UUID objectId) {
        return documentationService.listRestorations(objectId);
    }

    @PostMapping("/restorations")
    public ResponseEntity<RestorationResponse> createRestoration(
            @PathVariable UUID objectId,
            @Valid @RequestBody RestorationRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(documentationService.createRestoration(objectId, request));
    }

    @PutMapping("/restorations/{entryId}")
    public RestorationResponse updateRestoration(
            @PathVariable UUID objectId,
            @PathVariable UUID entryId,
            @Valid @RequestBody RestorationRequest request
    ) {
        return documentationService.updateRestoration(objectId, entryId, request);
    }

    @DeleteMapping("/restorations/{entryId}")
    public ResponseEntity<Void> deleteRestoration(@PathVariable UUID objectId, @PathVariable UUID entryId) {
        documentationService.deleteRestoration(objectId, entryId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/loans")
    public List<LoanResponse> listLoans(@PathVariable UUID objectId) {
        return documentationService.listLoans(objectId);
    }

    @PostMapping("/loans")
    public ResponseEntity<LoanResponse> createLoan(
            @PathVariable UUID objectId,
            @Valid @RequestBody LoanRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(documentationService.createLoan(objectId, request));
    }

    @PutMapping("/loans/{entryId}")
    public LoanResponse updateLoan(
            @PathVariable UUID objectId,
            @PathVariable UUID entryId,
            @Valid @RequestBody LoanRequest request
    ) {
        return documentationService.updateLoan(objectId, entryId, request);
    }

    @DeleteMapping("/loans/{entryId}")
    public ResponseEntity<Void> deleteLoan(@PathVariable UUID objectId, @PathVariable UUID entryId) {
        documentationService.deleteLoan(objectId, entryId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/research-reports")
    public List<ResearchReportResponse> listResearchReports(@PathVariable UUID objectId) {
        return documentationService.listResearchReports(objectId);
    }

    @PostMapping("/research-reports")
    public ResponseEntity<ResearchReportResponse> createResearchReport(
            @PathVariable UUID objectId,
            @Valid @RequestBody ResearchReportRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(documentationService.createResearchReport(objectId, request));
    }

    @PutMapping("/research-reports/{entryId}")
    public ResearchReportResponse updateResearchReport(
            @PathVariable UUID objectId,
            @PathVariable UUID entryId,
            @Valid @RequestBody ResearchReportRequest request
    ) {
        return documentationService.updateResearchReport(objectId, entryId, request);
    }

    @DeleteMapping("/research-reports/{entryId}")
    public ResponseEntity<Void> deleteResearchReport(@PathVariable UUID objectId, @PathVariable UUID entryId) {
        documentationService.deleteResearchReport(objectId, entryId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/art-historical-reports")
    public List<ArtHistoricalReportResponse> listArtHistoricalReports(@PathVariable UUID objectId) {
        return documentationService.listArtHistoricalReports(objectId);
    }

    @PostMapping("/art-historical-reports")
    public ResponseEntity<ArtHistoricalReportResponse> createArtHistoricalReport(
            @PathVariable UUID objectId,
            @Valid @RequestBody ArtHistoricalReportRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(documentationService.createArtHistoricalReport(objectId, request));
    }

    @PutMapping("/art-historical-reports/{entryId}")
    public ArtHistoricalReportResponse updateArtHistoricalReport(
            @PathVariable UUID objectId,
            @PathVariable UUID entryId,
            @Valid @RequestBody ArtHistoricalReportRequest request
    ) {
        return documentationService.updateArtHistoricalReport(objectId, entryId, request);
    }

    @DeleteMapping("/art-historical-reports/{entryId}")
    public ResponseEntity<Void> deleteArtHistoricalReport(@PathVariable UUID objectId, @PathVariable UUID entryId) {
        documentationService.deleteArtHistoricalReport(objectId, entryId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/documents")
    public List<ObjectDocumentResponse> listDocuments(@PathVariable UUID objectId) {
        return documentationService.listDocuments(objectId);
    }

    @PostMapping("/documents")
    public ResponseEntity<ObjectDocumentResponse> createDocument(
            @PathVariable UUID objectId,
            @Valid @RequestBody ObjectDocumentRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(documentationService.createDocument(objectId, request));
    }

    @DeleteMapping("/documents/{documentId}")
    public ResponseEntity<Void> deleteDocument(@PathVariable UUID objectId, @PathVariable UUID documentId) {
        documentationService.deleteDocument(objectId, documentId);
        return ResponseEntity.noContent().build();
    }
}
