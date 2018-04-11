package app.data.jpa.repository;

// import org.springframework.data.jpa.repository.JpaRepository;
import app.data.jpa.domain.Usex;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

// public interface UsexRepository{
public interface UsexRepository extends CrudRepository<Usex, Long>{
	// Usex add(Usex user);
	// Usex merge(Usex user);
 //    List<Usex> findByEmail(String email);
 //    List<Usex> findByEmailOnline(String email);
 //    Usex findByEmailandPasswd(String email, String passwd);
 //    int updateOnline(String username, Boolean online);
 //    int softDeleteById(Long id);
 //    Usex findById(Long id);
 //    List<Usex> findChildrensFromParent(Long userId);

    // List<Usex> findByEmail(String email);
    // List<Usex> findByEmailOnline(String email);
    Usex findByEmailAndPassword(String email, String passwd);
    Usex findByEmailAndPasswordAndActive(String email, String passwd,boolean active);
    // int updateOnline(String username, Boolean online);
    // int softDeleteById(Long id);
    Usex findById(Long id);
    List<Usex> findByParentId(Long userId);
    List<Usex> findByParentIdAndActive( Long userId, boolean active);
}