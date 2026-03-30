package tn.enicar.enicarconnect.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import tn.enicar.enicarconnect.model.*;
import tn.enicar.enicarconnect.repository.*;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepo;
    private final GroupRepository groupRepo;
    private final GroupMemberRepository memberRepo;
    private final PostRepository postRepo;
    private final CommentRepository commentRepo;
    private final PostLikeRepository likeRepo;
    private final PasswordEncoder passwordEncoder;
    private final ObjectMapper objectMapper;

    @Override
    public void run(String... args) throws Exception {
        if (userRepo.count() > 0) {
            log.info("Database already seeded. Skipping initialization.");
            return;
        }

        log.info("Starting comprehensive database initialization from seed-data.json...");

        InputStream inputStream = TypeReference.class.getResourceAsStream("/seed-data.json");
        Map<String, List<Map<String, Object>>> data = objectMapper.readValue(inputStream, new TypeReference<>() {
        });

        Map<Integer, User> userMap = new HashMap<>();
        Map<Integer, AppGroup> groupMap = new HashMap<>();
        Map<Integer, Post> postMap = new HashMap<>();

        // 1. Seed Users
        List<Map<String, Object>> usersData = data.get("users");
        for (Map<String, Object> uMap : usersData) {
            User user = User.builder()
                    .firstName((String) uMap.get("firstName"))
                    .lastName((String) uMap.get("lastName"))
                    .email((String) uMap.get("email"))
                    .password(passwordEncoder.encode((String) uMap.get("password")))
                    .role(Role.valueOf((String) uMap.get("role")))
                    .department((String) uMap.get("department"))
                    .level((String) uMap.get("level"))
                    .avatarColor((String) uMap.get("avatarColor"))
                    .avatarBg((String) uMap.get("avatarBg"))
                    .build();
            user = userRepo.save(user);
            userMap.put((Integer) uMap.get("id"), user);
            log.info("Created user: {}", user.getEmail());
        }

        // 2. Seed Groups
        List<Map<String, Object>> groupsData = data.get("groups");
        for (Map<String, Object> gMap : groupsData) {
            User creator = userMap.get((Integer) gMap.get("creatorId"));
            AppGroup group = AppGroup.builder()
                    .name((String) gMap.get("name"))
                    .description((String) gMap.get("description"))
                    .groupType(GroupType.valueOf((String) gMap.get("groupType")))
                    .privacy(GroupPrivacy.valueOf((String) gMap.get("privacy")))
                    .icon((String) gMap.get("icon"))
                    .iconColor((String) gMap.get("iconColor"))
                    .bannerGradient((String) gMap.get("bannerGradient"))
                    .creator(creator)
                    .build();
            group = groupRepo.save(group);
            groupMap.put((Integer) gMap.get("id"), group);

            // Add creator as Admin
            memberRepo.save(GroupMember.builder().group(group).user(creator).memberRole(MemberRole.ADMIN).build());
            log.info("Created group: {}", group.getName());
        }

        // 3. Seed Posts
        List<Map<String, Object>> postsData = data.get("posts");
        for (Map<String, Object> pMap : postsData) {
            User author = userMap.get((Integer) pMap.get("authorId"));
            Integer gId = (Integer) pMap.get("groupId");

            Post post = Post.builder()
                    .author(author)
                    .body((String) pMap.get("body"))
                    .visibility(Visibility.valueOf((String) pMap.get("visibility")))
                    .group(gId != null ? groupMap.get(gId) : null)
                    .build();

            // Extract hashtags automatically
            post.setHashtags(extractHashtags(post.getBody()));

            post = postRepo.save(post);
            postMap.put((Integer) pMap.get("id"), post);
            log.info("Created post ID: {} by {}", post.getId(), author.getEmail());
        }

        // 4. Seed Comments
        List<Map<String, Object>> commentsData = data.get("comments");
        for (Map<String, Object> cMap : commentsData) {
            Post post = postMap.get((Integer) cMap.get("postId"));
            User author = userMap.get((Integer) cMap.get("authorId"));

            Comment comment = Comment.builder()
                    .post(post)
                    .author(author)
                    .text((String) cMap.get("text"))
                    .build();
            commentRepo.save(comment);
        }

        // 5. Seed Likes
        List<Map<String, Object>> likesData = data.get("likes");
        for (Map<String, Object> lMap : likesData) {
            Post post = postMap.get((Integer) lMap.get("postId"));
            User user = userMap.get((Integer) lMap.get("userId"));

            likeRepo.save(PostLike.builder().post(post).user(user).build());
        }

        log.info("Comprehensive database initialization completed.");
    }

    private String extractHashtags(String body) {
        if (body == null)
            return null;
        java.util.Set<String> tags = new java.util.LinkedHashSet<>();
        java.util.regex.Matcher m = java.util.regex.Pattern.compile("#(\\w+)").matcher(body);
        while (m.find()) {
            tags.add(m.group(1).toLowerCase());
        }
        return tags.isEmpty() ? null : String.join(",", tags);
    }
}
