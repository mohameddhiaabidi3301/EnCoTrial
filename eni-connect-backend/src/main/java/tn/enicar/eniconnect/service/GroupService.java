package tn.enicar.eniconnect.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.enicar.eniconnect.model.Group;
import tn.enicar.eniconnect.model.User;
import tn.enicar.eniconnect.repository.GroupRepository;
import tn.enicar.eniconnect.repository.UserRepository;

import java.util.List;

@Service
public class GroupService {

    private static final Logger logger = LogManager.getLogger(GroupService.class);

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }

    public List<Group> getGroupsByCategory(String category) {
        return groupRepository.findByCategoryOrderByCreatedAtDesc(category);
    }

    public Group getGroupById(Long id) {
        return groupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Groupe non trouvé"));
    }

    public Group createGroup(Group group, Long creatorId) {
        User creator = userRepository.findById(creatorId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        group.setCreator(creator);
        group.getMembers().add(creator);

        Group saved = groupRepository.save(group);
        logger.info("Groupe créé : {} par {} (id={})", saved.getName(), creator.getFullName(), saved.getId());
        return saved;
    }

    public Group joinGroup(Long groupId, Long userId) {
        Group group = getGroupById(groupId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        group.getMembers().add(user);
        logger.info("User {} a rejoint le groupe {}", userId, groupId);
        return groupRepository.save(group);
    }

    public Group leaveGroup(Long groupId, Long userId) {
        Group group = getGroupById(groupId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        group.getMembers().remove(user);
        return groupRepository.save(group);
    }

    public void deleteGroup(Long id) {
        groupRepository.deleteById(id);
        logger.info("Groupe supprimé (id={})", id);
    }
}
