package com.greyhammer.erpservice.services;

import com.greyhammer.erpservice.commands.AddAttachmentCommand;
import com.greyhammer.erpservice.converters.AddAttachmentCommandToAttachmentConverter;
import com.greyhammer.erpservice.exceptions.AttachmentNotFoundException;
import com.greyhammer.erpservice.exceptions.ProjectNotFoundException;
import com.greyhammer.erpservice.exceptions.ProjectNotMatchException;
import com.greyhammer.erpservice.models.Attachment;
import com.greyhammer.erpservice.models.Project;
import com.greyhammer.erpservice.repositories.AttachmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AttachmentServiceImp implements  AttachmentService {
    AddAttachmentCommandToAttachmentConverter addAttachmentCommandToAttachmentConverter;
    AttachmentRepository attachmentRepository;
    ProjectService projectService;

    public AttachmentServiceImp(AddAttachmentCommandToAttachmentConverter addAttachmentCommandToAttachmentConverter, AttachmentRepository attachmentRepository, ProjectService projectService) {
        this.addAttachmentCommandToAttachmentConverter = addAttachmentCommandToAttachmentConverter;
        this.attachmentRepository = attachmentRepository;
        this.projectService = projectService;
    }

    @Override
    @Transactional
    public Attachment handleAddAttachmentCommand(Long projectId, AddAttachmentCommand command) throws ProjectNotFoundException {
        Project project = projectService.getProjectById(projectId);

        Attachment attachment = addAttachmentCommandToAttachmentConverter.convert(command);
        attachment.setProject(project);

        return attachmentRepository.save(attachment);
    }

    @Override
    public void deleteAttachment(Long projectId, Long attachmentId) throws AttachmentNotFoundException, ProjectNotMatchException {
        Attachment attachment = get(projectId, attachmentId);
        attachmentRepository.delete(attachment);
    }

    @Override
    public Attachment get(Long projectId, Long attachmentId) throws AttachmentNotFoundException, ProjectNotMatchException {
        Optional<Attachment> attachment = attachmentRepository.findById(attachmentId);

        if (attachment.isEmpty())
            throw new AttachmentNotFoundException();

        if (!attachment.get().getProject().getId().equals(projectId)) {
            throw new ProjectNotMatchException();
        }

        return attachment.get();
    }

}