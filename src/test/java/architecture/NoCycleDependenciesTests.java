package architecture;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;

@AnalyzeClasses(
        packages = "com.electiva.speakup",
        importOptions = ImportOption.DoNotIncludeTests.class
)
public class NoCycleDependenciesTests {

    @ArchTest
    static final ArchRule domain_modules_should_be_free_of_cycles =
            slices()
                    .matching("com.electiva.speakup.domain.model.(*)..")
                    .should().beFreeOfCycles()
                    .because("Domain modules should be free of cycles to ensure a clean and maintainable architecture.");

    @ArchTest
    static final ArchRule main_modules_should_be_free_of_cycles =
            slices()
                    .matching("com.electiva.speakup.(*)..")
                    .should().beFreeOfCycles()
                    .because("Main modules should be free of cycles to ensure a clean and maintainable architecture.");
}
