package tn.enicar.eniconnect.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import tn.enicar.eniconnect.model.Group;
import tn.enicar.eniconnect.model.User;
import tn.enicar.eniconnect.service.GroupService;

import java.util.List;

/**
 * Contrôleur des groupes — correspond au composant groups Angular.
 */
@RestController
@RequestMapping("/api/groups")
@CrossOrigin(origins = "*", maxAge = 3600)
public class GroupController {

    @Autowired
    private GroupService groupService;

    @GetMapping
    public ResponseEntity<List<Group>> getAllGroups() {
        return ResponseEntity.ok(groupService.getAllGroups());
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Group>> getGroupsByCategory(@PathVariable String category) {
        return ResponseEntity.ok(groupService.getGroupsByCategory(category));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Group> getGroupById(@PathVariable Long id) {
        return ResponseEntity.ok(groupService.getGroupById(id));
    }

    @PostMapping
    public ResponseEntity<Group> createGroup(@AuthenticationPrincipal User user,
            @RequestBody Group group) {
        return ResponseEntity.ok(groupService.createGroup(group, user.getId()));
    }

    @PostMapping("/{id}/join")
    public ResponseEntity<Group> joinGroup(@PathVariable Long id,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(groupService.joinGroup(id, user.getId()));
    }

    @DeleteMapping("/{id}/leave")
    public ResponseEntity<Group> leaveGroup(@PathVariable Long id,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(groupService.leaveGroup(id, user.getId()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGroup(@PathVariable Long id) {
        groupService.deleteGroup(id);
        return ResponseEntity.ok().build();
    }
}
