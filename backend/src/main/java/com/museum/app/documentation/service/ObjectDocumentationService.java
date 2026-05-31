package com.museum.app.documentation.service;

import com.museum.app.common.NotFoundException;
import com.museum.app.core.service.MuseumObjectService;
import com.museum.app.documentation.domain.ArtHistoricalReport;
import com.museum.app.documentation.domain.ConditionReport;
import com.museum.app.documentation.domain.Insurance;
import com.museum.app.documentation.domain.Loan;
import com.museum.app.documentation.domain.ObjectDocument;
import com.museum.app.documentation.domain.Provenance;
import com.museum.app.documentation.domain.ResearchReport;
import com.museum.app.documentation.domain.Restoration;
import com.museum.app.documentation.domain.RestorationStatus;
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
import com.museum.app.documentation.repository.ArtHistoricalReportRepository;
import com.museum.app.documentation.repository.ConditionReportRepository;
import com.museum.app.documentation.repository.InsuranceRepository;
import com.museum.app.documentation.repository.LoanRepository;
import com.museum.app.documentation.repository.ObjectDocumentRepository;
import com.museum.app.documentation.repository.ProvenanceRepository;
import com.museum.app.documentation.repository.ResearchReportRepository;
import com.museum.app.documentation.repository.RestorationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@Transactional
public class ObjectDocumentationService {

    private static final Set<String> LINKED_ENTITY_TYPES = Set.of(
            "condition_report", "restoration", "research_report",
            "art_historical_report", "loan", "provenance"
    );

    private final MuseumObjectService museumObjectService;
    private final ConditionReportRepository conditionReportRepository;
    private final ProvenanceRepository provenanceRepository;
    private final InsuranceRepository insuranceRepository;
    private final RestorationRepository restorationRepository;
    private final LoanRepository loanRepository;
    private final ResearchReportRepository researchReportRepository;
    private final ArtHistoricalReportRepository artHistoricalReportRepository;
    private final ObjectDocumentRepository objectDocumentRepository;

    public ObjectDocumentationService(
            MuseumObjectService museumObjectService,
            ConditionReportRepository conditionReportRepository,
            ProvenanceRepository provenanceRepository,
            InsuranceRepository insuranceRepository,
            RestorationRepository restorationRepository,
            LoanRepository loanRepository,
            ResearchReportRepository researchReportRepository,
            ArtHistoricalReportRepository artHistoricalReportRepository,
            ObjectDocumentRepository objectDocumentRepository
    ) {
        this.museumObjectService = museumObjectService;
        this.conditionReportRepository = conditionReportRepository;
        this.provenanceRepository = provenanceRepository;
        this.insuranceRepository = insuranceRepository;
        this.restorationRepository = restorationRepository;
        this.loanRepository = loanRepository;
        this.researchReportRepository = researchReportRepository;
        this.artHistoricalReportRepository = artHistoricalReportRepository;
        this.objectDocumentRepository = objectDocumentRepository;
    }

    // --- Condition reports ---

    @Transactional(readOnly = true)
    public List<ConditionReportResponse> listConditionReports(UUID objectId) {
        requireObject(objectId);
        return conditionReportRepository.findByObjectIdAndDeletedFalseOrderByReportDateDesc(objectId)
                .stream().map(this::toConditionResponse).toList();
    }

    public ConditionReportResponse createConditionReport(UUID objectId, ConditionReportRequest request) {
        requireObject(objectId);
        ConditionReport entity = new ConditionReport();
        entity.setObjectId(objectId);
        applyConditionRequest(entity, request);
        return toConditionResponse(conditionReportRepository.save(entity));
    }

    public ConditionReportResponse updateConditionReport(UUID objectId, UUID reportId, ConditionReportRequest request) {
        ConditionReport entity = findConditionReport(objectId, reportId);
        applyConditionRequest(entity, request);
        return toConditionResponse(conditionReportRepository.save(entity));
    }

    public void deleteConditionReport(UUID objectId, UUID reportId) {
        ConditionReport entity = findConditionReport(objectId, reportId);
        entity.setDeleted(true);
        conditionReportRepository.save(entity);
    }

    // --- Provenance ---

    @Transactional(readOnly = true)
    public List<ProvenanceResponse> listProvenance(UUID objectId) {
        requireObject(objectId);
        return provenanceRepository.findByObjectIdAndDeletedFalseOrderByFromDateDesc(objectId)
                .stream().map(this::toProvenanceResponse).toList();
    }

    public ProvenanceResponse createProvenance(UUID objectId, ProvenanceRequest request) {
        requireObject(objectId);
        Provenance entity = new Provenance();
        entity.setObjectId(objectId);
        applyProvenanceRequest(entity, request);
        return toProvenanceResponse(provenanceRepository.save(entity));
    }

    public ProvenanceResponse updateProvenance(UUID objectId, UUID id, ProvenanceRequest request) {
        Provenance entity = findProvenance(objectId, id);
        applyProvenanceRequest(entity, request);
        return toProvenanceResponse(provenanceRepository.save(entity));
    }

    public void deleteProvenance(UUID objectId, UUID id) {
        Provenance entity = findProvenance(objectId, id);
        entity.setDeleted(true);
        provenanceRepository.save(entity);
    }

    // --- Insurance ---

    @Transactional(readOnly = true)
    public List<InsuranceResponse> listInsurance(UUID objectId) {
        requireObject(objectId);
        return insuranceRepository.findByObjectIdAndDeletedFalseOrderByStartDateDesc(objectId)
                .stream().map(this::toInsuranceResponse).toList();
    }

    public InsuranceResponse createInsurance(UUID objectId, InsuranceRequest request) {
        requireObject(objectId);
        Insurance entity = new Insurance();
        entity.setObjectId(objectId);
        applyInsuranceRequest(entity, request);
        return toInsuranceResponse(insuranceRepository.save(entity));
    }

    public InsuranceResponse updateInsurance(UUID objectId, UUID id, InsuranceRequest request) {
        Insurance entity = findInsurance(objectId, id);
        applyInsuranceRequest(entity, request);
        return toInsuranceResponse(insuranceRepository.save(entity));
    }

    public void deleteInsurance(UUID objectId, UUID id) {
        Insurance entity = findInsurance(objectId, id);
        entity.setDeleted(true);
        insuranceRepository.save(entity);
    }

    // --- Restoration ---

    @Transactional(readOnly = true)
    public List<RestorationResponse> listRestorations(UUID objectId) {
        requireObject(objectId);
        return restorationRepository.findByObjectIdAndDeletedFalseOrderByStartDateDesc(objectId)
                .stream().map(this::toRestorationResponse).toList();
    }

    public RestorationResponse createRestoration(UUID objectId, RestorationRequest request) {
        requireObject(objectId);
        Restoration entity = new Restoration();
        entity.setObjectId(objectId);
        applyRestorationRequest(entity, request);
        return toRestorationResponse(restorationRepository.save(entity));
    }

    public RestorationResponse updateRestoration(UUID objectId, UUID id, RestorationRequest request) {
        Restoration entity = findRestoration(objectId, id);
        applyRestorationRequest(entity, request);
        return toRestorationResponse(restorationRepository.save(entity));
    }

    public void deleteRestoration(UUID objectId, UUID id) {
        Restoration entity = findRestoration(objectId, id);
        entity.setDeleted(true);
        restorationRepository.save(entity);
    }

    // --- Loan ---

    @Transactional(readOnly = true)
    public List<LoanResponse> listLoans(UUID objectId) {
        requireObject(objectId);
        return loanRepository.findByObjectIdAndDeletedFalseOrderByStartDateDesc(objectId)
                .stream().map(this::toLoanResponse).toList();
    }

    public LoanResponse createLoan(UUID objectId, LoanRequest request) {
        requireObject(objectId);
        Loan entity = new Loan();
        entity.setObjectId(objectId);
        applyLoanRequest(entity, request);
        return toLoanResponse(loanRepository.save(entity));
    }

    public LoanResponse updateLoan(UUID objectId, UUID id, LoanRequest request) {
        Loan entity = findLoan(objectId, id);
        applyLoanRequest(entity, request);
        return toLoanResponse(loanRepository.save(entity));
    }

    public void deleteLoan(UUID objectId, UUID id) {
        Loan entity = findLoan(objectId, id);
        entity.setDeleted(true);
        loanRepository.save(entity);
    }

    // --- Research reports ---

    @Transactional(readOnly = true)
    public List<ResearchReportResponse> listResearchReports(UUID objectId) {
        requireObject(objectId);
        return researchReportRepository.findByObjectIdAndDeletedFalseOrderByReportDateDesc(objectId)
                .stream().map(this::toResearchResponse).toList();
    }

    public ResearchReportResponse createResearchReport(UUID objectId, ResearchReportRequest request) {
        requireObject(objectId);
        ResearchReport entity = new ResearchReport();
        entity.setObjectId(objectId);
        applyResearchRequest(entity, request);
        return toResearchResponse(researchReportRepository.save(entity));
    }

    public ResearchReportResponse updateResearchReport(UUID objectId, UUID id, ResearchReportRequest request) {
        ResearchReport entity = findResearchReport(objectId, id);
        applyResearchRequest(entity, request);
        return toResearchResponse(researchReportRepository.save(entity));
    }

    public void deleteResearchReport(UUID objectId, UUID id) {
        ResearchReport entity = findResearchReport(objectId, id);
        entity.setDeleted(true);
        researchReportRepository.save(entity);
    }

    // --- Art-historical reports ---

    @Transactional(readOnly = true)
    public List<ArtHistoricalReportResponse> listArtHistoricalReports(UUID objectId) {
        requireObject(objectId);
        return artHistoricalReportRepository.findByObjectIdAndDeletedFalseOrderByReportDateDesc(objectId)
                .stream().map(this::toArtHistoricalResponse).toList();
    }

    public ArtHistoricalReportResponse createArtHistoricalReport(UUID objectId, ArtHistoricalReportRequest request) {
        requireObject(objectId);
        ArtHistoricalReport entity = new ArtHistoricalReport();
        entity.setObjectId(objectId);
        applyArtHistoricalRequest(entity, request);
        return toArtHistoricalResponse(artHistoricalReportRepository.save(entity));
    }

    public ArtHistoricalReportResponse updateArtHistoricalReport(UUID objectId, UUID id, ArtHistoricalReportRequest request) {
        ArtHistoricalReport entity = findArtHistoricalReport(objectId, id);
        applyArtHistoricalRequest(entity, request);
        return toArtHistoricalResponse(artHistoricalReportRepository.save(entity));
    }

    public void deleteArtHistoricalReport(UUID objectId, UUID id) {
        ArtHistoricalReport entity = findArtHistoricalReport(objectId, id);
        entity.setDeleted(true);
        artHistoricalReportRepository.save(entity);
    }

    // --- Documents ---

    @Transactional(readOnly = true)
    public List<ObjectDocumentResponse> listDocuments(UUID objectId) {
        requireObject(objectId);
        return objectDocumentRepository.findByObjectIdAndDeletedFalseOrderByUploadedAtDesc(objectId)
                .stream().map(this::toDocumentResponse).toList();
    }

    public ObjectDocumentResponse createDocument(UUID objectId, ObjectDocumentRequest request) {
        requireObject(objectId);
        validateLinkedEntityType(request.linkedEntityType());
        ObjectDocument entity = new ObjectDocument();
        entity.setObjectId(objectId);
        entity.setLinkedEntityType(request.linkedEntityType());
        entity.setLinkedEntityId(request.linkedEntityId());
        entity.setDocumentKind(request.documentKind());
        entity.setTitle(request.title());
        entity.setFileUri(request.fileUri());
        entity.setMimeType(request.mimeType());
        entity.setFileSizeBytes(request.fileSizeBytes());
        entity.setChecksumSha256(request.checksumSha256());
        entity.setUploadedAt(Instant.now());
        return toDocumentResponse(objectDocumentRepository.save(entity));
    }

    public void deleteDocument(UUID objectId, UUID id) {
        ObjectDocument entity = objectDocumentRepository.findByIdAndObjectIdAndDeletedFalse(id, objectId)
                .orElseThrow(() -> new NotFoundException("Document not found: " + id));
        entity.setDeleted(true);
        objectDocumentRepository.save(entity);
    }

    private void requireObject(UUID objectId) {
        museumObjectService.requireActiveObject(objectId);
    }

    private void validateLinkedEntityType(String type) {
        if (!LINKED_ENTITY_TYPES.contains(type)) {
            throw new IllegalArgumentException("Invalid linkedEntityType: " + type);
        }
    }

    private ConditionReport findConditionReport(UUID objectId, UUID id) {
        return conditionReportRepository.findByIdAndObjectIdAndDeletedFalse(id, objectId)
                .orElseThrow(() -> new NotFoundException("Condition report not found: " + id));
    }

    private Provenance findProvenance(UUID objectId, UUID id) {
        return provenanceRepository.findByIdAndObjectIdAndDeletedFalse(id, objectId)
                .orElseThrow(() -> new NotFoundException("Provenance entry not found: " + id));
    }

    private Insurance findInsurance(UUID objectId, UUID id) {
        return insuranceRepository.findByIdAndObjectIdAndDeletedFalse(id, objectId)
                .orElseThrow(() -> new NotFoundException("Insurance record not found: " + id));
    }

    private Restoration findRestoration(UUID objectId, UUID id) {
        return restorationRepository.findByIdAndObjectIdAndDeletedFalse(id, objectId)
                .orElseThrow(() -> new NotFoundException("Restoration record not found: " + id));
    }

    private Loan findLoan(UUID objectId, UUID id) {
        return loanRepository.findByIdAndObjectIdAndDeletedFalse(id, objectId)
                .orElseThrow(() -> new NotFoundException("Loan record not found: " + id));
    }

    private ResearchReport findResearchReport(UUID objectId, UUID id) {
        return researchReportRepository.findByIdAndObjectIdAndDeletedFalse(id, objectId)
                .orElseThrow(() -> new NotFoundException("Research report not found: " + id));
    }

    private ArtHistoricalReport findArtHistoricalReport(UUID objectId, UUID id) {
        return artHistoricalReportRepository.findByIdAndObjectIdAndDeletedFalse(id, objectId)
                .orElseThrow(() -> new NotFoundException("Art-historical report not found: " + id));
    }

    private void applyConditionRequest(ConditionReport entity, ConditionReportRequest request) {
        entity.setReportDate(request.reportDate());
        entity.setCreatedByUserId(request.createdByUserId());
        entity.setConservatorPersonId(request.conservatorPersonId());
        entity.setConditionCode(request.conditionCode());
        entity.setDamageSeverity(request.damageSeverity());
        entity.setSummary(request.summary());
        entity.setRecommendations(request.recommendations());
        entity.setCanBeExhibited(request.canBeExhibited());
        entity.setCanBeTransported(request.canBeTransported());
    }

    private void applyProvenanceRequest(Provenance entity, ProvenanceRequest request) {
        entity.setOwnerName(request.ownerName());
        entity.setOwnerPersonId(request.ownerPersonId());
        entity.setOwnerOrganizationId(request.ownerOrganizationId());
        entity.setFromDate(request.fromDate());
        entity.setToDate(request.toDate());
        entity.setOrigin(request.origin());
        entity.setAcquisitionType(request.acquisitionType());
        entity.setNotes(request.notes());
    }

    private void applyInsuranceRequest(Insurance entity, InsuranceRequest request) {
        entity.setInsurer(request.insurer());
        entity.setInsuredValue(request.insuredValue());
        entity.setCurrency(request.currency());
        entity.setContractNumber(request.contractNumber());
        entity.setStartDate(request.startDate());
        entity.setEndDate(request.endDate());
        entity.setSpecialConditions(request.specialConditions());
    }

    private void applyRestorationRequest(Restoration entity, RestorationRequest request) {
        entity.setRestauratorUserId(request.restauratorUserId());
        entity.setRestauratorPersonId(request.restauratorPersonId());
        entity.setStartDate(request.startDate());
        entity.setEndDate(request.endDate());
        if (request.status() != null) {
            entity.setStatus(request.status());
        } else if (entity.getStatus() == null) {
            entity.setStatus(RestorationStatus.draft);
        }
        entity.setMeasures(request.measures());
        entity.setTreatmentPlan(request.treatmentPlan());
        entity.setMaterialsUsed(request.materialsUsed());
        entity.setReversibilityNotes(request.reversibilityNotes());
        entity.setEthicalAssessment(request.ethicalAssessment());
        entity.setReportSummary(request.reportSummary());
        entity.setCost(request.cost());
        entity.setCurrency(request.currency());
        entity.setConditionReportBeforeId(request.conditionReportBeforeId());
        entity.setConditionReportAfterId(request.conditionReportAfterId());
        entity.setNotes(request.notes());
        entity.setMetadata(request.metadata());
    }

    private void applyLoanRequest(Loan entity, LoanRequest request) {
        entity.setInstitution(request.institution());
        entity.setInstitutionOrganizationId(request.institutionOrganizationId());
        entity.setLoanType(request.loanType());
        entity.setStartDate(request.startDate());
        entity.setEndDate(request.endDate());
        entity.setInsuranceNotes(request.insuranceNotes());
        entity.setConditionBefore(request.conditionBefore());
        entity.setConditionAfter(request.conditionAfter());
        entity.setConditionReportBeforeId(request.conditionReportBeforeId());
        entity.setConditionReportAfterId(request.conditionReportAfterId());
        entity.setNotes(request.notes());
    }

    private void applyResearchRequest(ResearchReport entity, ResearchReportRequest request) {
        entity.setReportDate(request.reportDate());
        entity.setResearchType(request.researchType());
        entity.setTitle(request.title());
        entity.setInstitution(request.institution());
        entity.setLabName(request.labName());
        entity.setResearcherPersonId(request.researcherPersonId());
        entity.setResearcherUserId(request.researcherUserId());
        entity.setMethodology(request.methodology());
        entity.setSampleDescription(request.sampleDescription());
        entity.setResults(request.results());
        entity.setConclusions(request.conclusions());
        if (request.status() != null) {
            entity.setStatus(request.status());
        }
        entity.setMetadata(request.metadata());
    }

    private void applyArtHistoricalRequest(ArtHistoricalReport entity, ArtHistoricalReportRequest request) {
        entity.setReportDate(request.reportDate());
        entity.setTitle(request.title());
        entity.setAuthorPersonId(request.authorPersonId());
        entity.setAuthorUserId(request.authorUserId());
        entity.setAttribution(request.attribution());
        entity.setAttributionCertainty(request.attributionCertainty());
        entity.setDatingText(request.datingText());
        entity.setDatingFrom(request.datingFrom());
        entity.setDatingTo(request.datingTo());
        entity.setStylePeriod(request.stylePeriod());
        entity.setSubjectIconography(request.subjectIconography());
        entity.setTechniqueAnalysis(request.techniqueAnalysis());
        entity.setHistoricalContext(request.historicalContext());
        entity.setBibliography(request.bibliography());
        entity.setExhibitionHistorySummary(request.exhibitionHistorySummary());
        if (request.status() != null) {
            entity.setStatus(request.status());
        }
        if (request.versionNumber() != null) {
            entity.setVersionNumber(request.versionNumber());
        }
        entity.setSupersedesReportId(request.supersedesReportId());
        entity.setMetadata(request.metadata());
    }

    private ConditionReportResponse toConditionResponse(ConditionReport entity) {
        return new ConditionReportResponse(
                entity.getId(), entity.getObjectId(), entity.getReportDate(),
                entity.getConditionCode(), entity.getDamageSeverity(),
                entity.getSummary(), entity.getRecommendations(),
                entity.getCanBeExhibited(), entity.getCanBeTransported(),
                entity.getVersion(), entity.getCreatedAt()
        );
    }

    private ProvenanceResponse toProvenanceResponse(Provenance entity) {
        return new ProvenanceResponse(
                entity.getId(), entity.getObjectId(), entity.getOwnerName(),
                entity.getFromDate(), entity.getToDate(), entity.getOrigin(),
                entity.getAcquisitionType(), entity.getNotes(), entity.getVersion()
        );
    }

    private InsuranceResponse toInsuranceResponse(Insurance entity) {
        return new InsuranceResponse(
                entity.getId(), entity.getObjectId(), entity.getInsurer(),
                entity.getInsuredValue(), entity.getCurrency(), entity.getContractNumber(),
                entity.getStartDate(), entity.getEndDate(), entity.getVersion()
        );
    }

    private RestorationResponse toRestorationResponse(Restoration entity) {
        return new RestorationResponse(
                entity.getId(), entity.getObjectId(), entity.getStatus(),
                entity.getStartDate(), entity.getEndDate(),
                entity.getTreatmentPlan(), entity.getReportSummary(),
                entity.getConditionReportBeforeId(), entity.getConditionReportAfterId(),
                entity.getVersion()
        );
    }

    private LoanResponse toLoanResponse(Loan entity) {
        return new LoanResponse(
                entity.getId(), entity.getObjectId(), entity.getInstitution(),
                entity.getLoanType(), entity.getStartDate(), entity.getEndDate(),
                entity.getVersion()
        );
    }

    private ResearchReportResponse toResearchResponse(ResearchReport entity) {
        return new ResearchReportResponse(
                entity.getId(), entity.getObjectId(), entity.getReportDate(),
                entity.getResearchType(), entity.getTitle(),
                entity.getResults(), entity.getConclusions(),
                entity.getStatus(), entity.getVersion()
        );
    }

    private ArtHistoricalReportResponse toArtHistoricalResponse(ArtHistoricalReport entity) {
        return new ArtHistoricalReportResponse(
                entity.getId(), entity.getObjectId(), entity.getReportDate(),
                entity.getTitle(), entity.getAttribution(), entity.getAttributionCertainty(),
                entity.getDatingText(), entity.getStylePeriod(), entity.getStatus(),
                entity.getVersionNumber(), entity.getSupersedesReportId(), entity.getVersion()
        );
    }

    private ObjectDocumentResponse toDocumentResponse(ObjectDocument entity) {
        return new ObjectDocumentResponse(
                entity.getId(), entity.getObjectId(),
                entity.getLinkedEntityType(), entity.getLinkedEntityId(),
                entity.getDocumentKind(), entity.getTitle(), entity.getFileUri(),
                entity.getMimeType(), entity.getUploadedAt(), entity.getVersion()
        );
    }
}
