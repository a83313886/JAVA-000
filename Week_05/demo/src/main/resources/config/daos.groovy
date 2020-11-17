import com.example.demo.domain.Klass
import com.example.demo.domain.Student

beans {
    student1(Student) {
        name = "John"
    }

    student2(Student) {
        name = "Justin"
    }

    klass(Klass) {
        students = [student1, student2]
    }


}