(ns ga4gh.megaschema.schema)

(def schema
  {:EvidenceLine
   {:type "object",
    :inherits "InformationEntity",
    :maturity "trial use",
    :description
    "An independent, evidence-based argument that may support or refute the validity of a specific Proposition. The strength and direction of this argument is based on an interpretation of one or more pieces of information as evidence for or against the target Proposition.",
    :comment
    "Evidence Lines are used to capture various pieces of information (i.e. 'evidence items') that are assessed together as an argument for or against some 'target proposition' - to report the direction (supports or disputes) and strength (e.g. strong, moderate, weak) that the argument is determined to make. For example, the various allele counts and population frequency calculations for the BRCA2 c.8023A>G variant in the ExAC database are evidence items that may be collectively assessed to build an EvidenceLine making argument of 'moderate' strength that 'supports' a target Proposition that the variant is pathogenic for Breast Cancer.\nEvidence Lines are flexible with respect to the granularity of arguments they support, and the scope of evidence items they can collectively assess. Narrow scoping will bucket available evidence into many, fine-grained Evidence Lines that make the most atomic independently meaningful arguments possible. The ACMG Variant Pathogenicity Interpretation Guidelines are an example of a fairly fine-grained evidence interpretation framework. Broader scoping approaches may organize the same available evidence into fewer Evidence Lines that build and assess less atomic arguments based on a wider and more diverse set of evidence items. For example, CIViC curators assess the strength and direction of evidence items at the level of *all information reported in a publication for a specific study* - which can encompass many different results and evidence types that under more fine-grained interpretation approaches might be split apart and assessed as separate lines of evidence.",
    :properties
    {:type
     {:extends "type",
      :const "EvidenceLine",
      :default "EvidenceLine",
      :description "MUST be \"EvidenceLine\"."},
     :targetProposition
     {:ref "#/$defs/Proposition",
      :description
      "The possible fact against which evidence items contained in an Evidence Line were collectively evaluated, in determining the overall strength and direction of support they provide. For example, in an ACMG Guideline-based assessment of variant pathogenicity, the support provided by distinct lines of evidence are assessed against a target proposition that the variant is pathogenic for a specific disease."},
     :hasEvidenceItems
     {:type "array",
      :ordered false,
      :items
      {:anyOf
       [{:ref "#/$defs/StudyResult"}
        {:ref "#/$defs/Statement"}
        {:ref "#/$defs/EvidenceLine"}
        {:refCurie "gks.core:iriReference"}]},
      :description
      "An individual piece of information that was evaluated as evidence in building the argument represented by an Evidence Line.",
      :comment
      "A given Evidence Line may be supported by one or many individual evidence items. What matters is that all evidence items in a given Evidence Line get collectively assessed and assigned direction and strength as a single argument for or against a target Proposition.\nDifferent types and scales of information entity can serve as evidence (experimental measurements or observations, data tables or figures, images, prior assertions, etc.) Only when assessed as evidence do we consider the information to be an 'Evidence Item'.  For example, a piece of population frequency data about BRCA2 c.8023A>G  becomes an Evidence Item when it is assessed for the support it may offer for a target Proposition (e.g. the prospect of the variant’s pathogenicity)."},
     :directionOfEvidenceProvided
     {:type "string",
      :enum ["supports" "neutral" "disputes"],
      :description
      "The direction of support that the Evidence Line is determined to provide toward its target Proposition (supports, disputes, neutral)"},
     :strengthOfEvidenceProvided
     {:refCurie "gks.core:MappableConcept",
      :description
      "The strength of support that an Evidence Line is determined to provide for or against its target Proposition, evaluated relative to the direction indicated by the directionOfEvidenceProvided value.",
      :comment
      "Values of this attribute can be defined by for a given profile based on domain/application needs, but should be framed in qualitative terms (e.g. 'strong', 'moderate', 'weak'). The 'scoreOfEvidenceProvided' attribute can be used to report quantitative assessments of evidence provided."},
     :scoreOfEvidenceProvided
     {:type "number",
      :description
      "A quantitative score indicating the strength of support that an Evidence Line is determined to provide for or against its target Proposition, evaluated relative to the direction indicated by the directionOfEvidenceProvided value."},
     :evidenceOutcome
     {:refCurie "gks.core:MappableConcept",
      :description
      "A term summarizing the overall outcome of the evidence assessment represented by the Evidence Line, in terms of the direction and strength of support it provides for or against the target Proposition."}},
    :required ["directionOfEvidenceProvided"],
    :source :ga4gh/va-spec},
   :Constraint
   {:maturity "trial use",
    :description
    "Constraints are used to construct an intensional semantics of categorical variant types.",
    :oneOf
    [{:ref "#/$defs/DefiningAlleleConstraint"}
     {:ref "#/$defs/DefiningLocationConstraint"}
     {:ref "#/$defs/CopyCountConstraint"}
     {:ref "#/$defs/CopyChangeConstraint"}
     {:ref "#/$defs/FeatureContextConstraint"}],
    :heritableProperties {:type {:type "string"}},
    :heritableRequired ["type"],
    :source :ga4gh/cat-vrs},
   :date
   {:type "string",
    :maturity "trial use",
    :description
    "A string is valid against this format if it represents a date in the following format: YYYY-MM-DD.",
    :format "date",
    :source :ga4gh/gks-core},
   :Proposition
   {:inherits "gks-core:Entity",
    :maturity "trial use",
    :description
    "An abstract entity representing a possible fact that may be true or false. As abstract entities, Propositions capture a 'sharable' piece of meaning whose identify and existence is independent of space and time, or whether it is ever asserted to be true by some agent.",
    :comment
    "Propositions are taken to represent the semantic content of sentences or lexical entities formulated in a natural language. As abstract entities, Propositions capture a 'sharable' piece of meaning that are used in two specific contexts on the VA model.  In Statements, a Proposition  encapsulate the meaning of what is put forth as true or false, or otherwise subjected to an assessment of the level of evidence supporting it.  In Evidence Lines, a Proposition captures the possible fact that a set of evidence items is evaluated as an argument for or against.",
    :heritableProperties
    {:subject
     {:oneOf [{:type "object"}],
      :description
      "The Entity or concept about which the Proposition is made.",
      :comment
      "In profiles for most VA-Spec implementations, the subject will be a some type of genetic variation. However, the Core VA model is domain-agnostic, and supports Propositions about any type of Entity. Some data providers may want to make statements about other entities or concepts that represent evidence for a Propositions about genetic variation (e.g. a statement that a gene is valid for some disease is one type of evidence that may support the pathogenicity of a variant that affects that gene)."},
     :predicate
     {:type "string",
      :description
      "The relationship declared to hold between the subject and the object of the Proposition.",
      :comment
      "When applied to represent a particular type of Proposition (via 'Profiling'), implementers can define a value set of predicates for the relationships relevant in the domain."},
     :object
     {:oneOf [{:type "object"}],
      :description
      "An Entity or concept that is related to the subject of a Proposition via its predicate.",
      :comment
      "The object of a Proposition can be any Entity or concept that is related to the subject. When the subject is a genetic variation, the object is often a disease, phenotype, therapeutic intervention, or gene."}},
    :heritableRequired ["subject" "predicate" "object"],
    :source :ga4gh/va-spec},
   :ExperimentalVariantFunctionalImpactStudyResult
   {:inherits "StudyResult",
    :maturity "trial use",
    :type "object",
    :description
    "A StudyResult that reports a functional impact score from a variant functional assay or study.",
    :properties
    {:type
     {:extends "type",
      :const "ExperimentalVariantFunctionalImpactStudyResult",
      :default "ExperimentalVariantFunctionalImpactStudyResult",
      :description
      "MUST be \"ExperimentalVariantFunctionalImpactStudyResult\"."},
     :focusVariant
     {:extends "focus",
      :oneOf
      [{:refCurie "vrs:MolecularVariation"}
       {:refCurie "gks.core:iriReference"}],
      :description
      "The genetic variant for which a functional impact score is generated."},
     :functionalImpactScore
     {:type "number",
      :description
      "The score of the variant impact measured in the assay or study."},
     :specifiedBy
     {:extends "specifiedBy",
      :description
      "The assay that was performed to generate the reported functional impact score."},
     :sourceDataSet
     {:extends "sourceDataSet",
      :description
      "The full data set that provided the reported the functional impact score."}},
    :source :ga4gh/va-spec},
   :LiteralSequenceExpression
   {:maturity "trial use",
    :inherits "SequenceExpression",
    :ga4gh {:inherent ["sequence"]},
    :description "An explicit expression of a Sequence.",
    :type "object",
    :properties
    {:type
     {:extends "type",
      :const "LiteralSequenceExpression",
      :default "LiteralSequenceExpression",
      :description "MUST be \"LiteralSequenceExpression\""},
     :sequence
     {:ref "#/$defs/sequenceString",
      :description "the literal sequence"}},
    :required ["sequence"],
    :source :ga4gh/vrs},
   :VariantOncogenicityProposition
   {:inherits "ClinicalVariantProposition",
    :maturity "trial use",
    :type "object",
    :description
    "A proposition describing the role of a variant in causing a tumor type.",
    :properties
    {:type
     {:extends "type",
      :const "VariantOncogenicityProposition",
      :default "VariantOncogenicityProposition",
      :description "MUST be \"VariantOncogenicityProposition\"."},
     :predicate {:extends "predicate", :default "isCausalFor"},
     :objectTumorType
     {:extends "object",
      :oneOf [{:ref "Condition"} {:refCurie "gks.core:iriReference"}],
      :description
      "The tumor type for which the variant impact is evaluated."}},
    :required ["predicate" "objectTumorType"],
    :source :ga4gh/va-spec},
   :Variation
   {:maturity "trial use",
    :inherits "Ga4ghIdentifiableObject",
    :description
    "A representation of the state of one or more biomolecules.",
    :oneOf
    [{:ref "#/$defs/MolecularVariation"}
     {:ref "#/$defs/SystemicVariation"}],
    :heritableProperties
    {:expressions
     {:type "array",
      :ordered false,
      :items {:ref "#/$defs/Expression"}}},
    :discriminator {:propertyName "type"},
    :source :ga4gh/vrs},
   :DataSet
   {:type "object",
    :inherits "gks-core:Entity",
    :maturity "trial use",
    :description
    "A collection of related data items or records that are organized together in a common format or structure, to enable their computational manipulation as a unit.",
    :comment
    "Instances of this class represent a specific version of a given dataset. Examples include the gnomAD version 3.1.2 dataset carrying allele population frequencies, or a specific version of a VCF file that describes variations observed in a particular patient and various annotations made on them, or a SIFT dataset of computational predictions functional impact for a set of variants.",
    :properties
    {:type
     {:extends "type",
      :const "DataSet",
      :default "DataSet",
      :description "MUST be \"DataSet\"."},
     :subtype
     {:maturity "draft",
      :refCurie "gks.core:MappableConcept",
      :description
      "A specific type of data set the DataSet instance represents (e.g. a 'clinical data set', a 'sequencing data set', a 'gene expression data set', a 'genome annotation data set')",
      :comment
      "This attribute can be used to report a specific type for the DataSet, in cases where a model does not define DataSet subclasses for this purpose. Implementers can define their own set of data set type codes/terms, to match the needs of the domain or application."},
     :reportedIn
     {:oneOf
      [{:ref "#/$defs/Document"} {:refCurie "gks.core:iriReference"}],
      :description "A document in which the the Method is reported."},
     :releaseDate
     {:refCurie "gks.core:date",
      :description
      "Indicates the date a version of a DataSet was formally released.",
      :comment
      "This attribute may apply to version and distribution-level DataSet representations. It refers to when the data set was released, which may be different than the data set was generated."},
     :version
     {:type "string",
      :description
      "The version of the DataSet, as assigned by its creator.",
      :comment
      "This property can capture version information for resources such as data sets and documents that get published and released as a unit for community use. These may go through rounds of revisions that add or modify content, but don’t change the identity of the resource. Use this attribute in cases where version is not reflected in an identifier associated with the data set."},
     :license
     {:refCurie "gks.core:MappableConcept",
      :description
      "A specific license that dictates legal permissions for how a data set can be used (by whom, where, for what purposes, with what additional requirements, etc.)",
      :comment
      "Where possible, provide a URL pointing to the license or a description of it (e.g. \"https://creativecommons.org/licenses/by/4.0/\"). Otherwise, provide a free-text name or description of the license (e.g. \"CC-BY\")."}},
    :source :ga4gh/va-spec},
   :LengthExpression
   {:maturity "draft",
    :inherits "SequenceExpression",
    :ga4gh {:inherent ["length"]},
    :type "object",
    :description "A sequence expressed only by its length.",
    :properties
    {:type
     {:extends "type",
      :const "LengthExpression",
      :default "LengthExpression",
      :description "MUST be \"LengthExpression\""},
     :length
     {:description "The length of the sequence.",
      :oneOf [{:ref "#/$defs/Range"} {:type "integer"}]}},
    :source :ga4gh/vrs},
   :ReferenceLengthExpression
   {:maturity "trial use",
    :inherits "SequenceExpression",
    :ga4gh {:inherent ["length" "repeatSubunitLength"]},
    :description
    "An expression of a sequence that is derived from repeating a subsequence of an associated :ref:`SequenceLocation`.",
    :type "object",
    :properties
    {:type
     {:extends "type",
      :const "ReferenceLengthExpression",
      :default "ReferenceLengthExpression",
      :description "MUST be \"ReferenceLengthExpression\""},
     :length
     {:oneOf [{:type "integer"} {:ref "#/$defs/Range"}],
      :description "The number of residues in the expressed sequence."},
     :sequence
     {:ref "#/$defs/sequenceString",
      :description
      "the literal :ref:`Sequence` encoded by the Reference Length Expression."},
     :repeatSubunitLength
     {:type "integer",
      :description "The number of residues in the repeat subunit."}},
    :required ["length" "repeatSubunitLength"],
    :source :ga4gh/vrs},
   :sequenceString
   {:maturity "trial use",
    :description
    "A character string of :ref:`residues <residue>` that represents a biological sequence using the conventional sequence order (5’-to-3’ for nucleic acid sequences, and amino-to-carboxyl for amino acid sequences). IUPAC ambiguity codes are permitted in Sequence Strings.",
    :type "string",
    :pattern "^[A-Z*\\-]*$",
    :source :ga4gh/vrs},
   :StudyGroup
   {:type "object",
    :inherits "gks-core:Entity",
    :maturity "trial use",
    :description
    "A collection of individuals or specimens from the same taxonomic class, selected for analysis in a scientific study based on their exhibiting one or more common characteristics  (e.g. species, race, age, gender, disease state, income). May be referred to as a 'cohort' or 'population' in specific research settings.",
    :comment
    "A Study Group may include all participants in a given study, or specific subsets that are designated based on shared roles or characteristics.",
    :properties
    {:type
     {:extends "type",
      :const "StudyGroup",
      :default "StudyGroup",
      :description "Must be \"StudyGroup\""},
     :memberCount
     {:maturity "draft",
      :comment
      "this is draft until we see utility in real-world datasets",
      :type "integer",
      :description
      "The total number of individual members in the StudyGroup."},
     :characteristics
     {:type "array",
      :items {:refCurie "gks.core:MappableConcept"},
      :ordered false,
      :description
      "A feature or role shared by all members of the StudyGroup, representing a criterion for membership in the group.",
      :comment
      "We should evaluate this for use with boolean operators before going normative"}},
    :source :ga4gh/va-spec},
   :SequenceExpression
   {:maturity "trial use",
    :inherits "gks-core:Entity",
    :ga4gh {:inherent ["type"]},
    :description "An expression describing a :ref:`Sequence`.",
    :oneOf
    [{:ref "#/$defs/LiteralSequenceExpression"}
     {:ref "#/$defs/ReferenceLengthExpression"}
     {:ref "#/$defs/LengthExpression"}],
    :discriminator {:propertyName "type"},
    :heritableProperties
    {:type
     {:extends "type",
      :description
      "The SequenceExpression class type. MUST match child class type."}},
    :heritableRequired ["type"],
    :source :ga4gh/vrs},
   :Element
   {:maturity "trial use",
    :description
    "The base definition for all identifiable data objects.",
    :heritableProperties
    {:id
     {:type "string",
      :description
      "The 'logical' identifier of the data element in the system of record, e.g. a UUID.  This 'id' is unique within a given system, but may or may not be globally unique outside the system. It is used within a system to reference an object from another."},
     :extensions
     {:type "array",
      :ordered false,
      :items {:ref "#/$defs/Extension"},
      :description
      "A list of extensions to the Entity, that allow for capture of information not directly supported by elements defined in the model.",
      :comment
      "Extension objects have a key-value data structure that allows definition of custom fields in the data itself. Extensions are not expected to be natively understood, but may be used for pre-negotiated exchange of message attributes between systems."}},
    :source :ga4gh/gks-core},
   :SubjectVariantProposition
   {:inherits "Proposition",
    :maturity "trial use",
    :description
    "A :ref:`Proposition` that has a variant as the subject.",
    :oneOf
    [{:ref "#/$defs/ExperimentalVariantFunctionalImpactProposition"}
     {:ref "#/$defs/VariantPathogenicityProposition"}
     {:ref "#/$defs/VariantDiagnosticProposition"}
     {:ref "#/$defs/VariantPrognosticProposition"}
     {:ref "#/$defs/VariantOncogenicityProposition"}
     {:ref "#/$defs/VariantTherapeuticResponseProposition"}],
    :heritableProperties
    {:subjectVariant
     {:extends "subject",
      :oneOf
      [{:refCurie "vrs:MolecularVariation"}
       {:refCurie "cat-vrs:CategoricalVariant"}
       {:refCurie "gks.core:iriReference"}],
      :description "A variant that is the subject of the Proposition."}},
    :source :ga4gh/va-spec},
   :VariantPathogenicityProposition
   {:inherits "ClinicalVariantProposition",
    :maturity "trial use",
    :type "object",
    :description
    "A proposition describing the role of a variant in causing a heritable condition.",
    :properties
    {:type
     {:extends "type",
      :const "VariantPathogenicityProposition",
      :default "VariantPathogenicityProposition",
      :description "Must be \"VariantPathogenicityProposition\""},
     :predicate {:extends "predicate", :default "isCausalFor"},
     :objectCondition
     {:extends "object",
      :oneOf [{:ref "Condition"} {:refCurie "gks.core:iriReference"}],
      :description
      "The :ref:`Condition` for which the variant impact is stated."},
     :penetranceQualifier
     {:refCurie "gks.core:MappableConcept",
      :description
      "Reports the penetrance of the pathogenic effect - i.e. the extent to which the variant impact is expressed by individuals carrying it as a measure of the proportion of carriers exhibiting the condition."},
     :modeOfInheritanceQualifier
     {:refCurie "gks.core:MappableConcept",
      :description
      "Reports a pattern of inheritance expected for the pathogenic effect of the variant. Consider using terms or codes from community terminologies here - e.g. terms from the 'Mode of inheritance' branch of the Human Phenotype Ontology such as HP:0000006 (autosomal dominant inheritance)."}},
    :required ["predicate" "objectCondition"],
    :source :ga4gh/va-spec},
   :iriReference
   {:type "string",
    :maturity "trial use",
    :description
    "An IRI Reference (either an IRI or a relative-reference), according to `RFC3986 section 4.1 <https://datatracker.ietf.org/doc/html/rfc3986#section-4.1>`_ and `RFC3987 section 2.1 <https://datatracker.ietf.org/doc/html/rfc3987#section-2.1>`_. MAY be a JSON Pointer as an IRI fragment, as described by `RFC6901 section 6 <https://datatracker.ietf.org/doc/html/rfc6901#section-6>`_.",
    :format "iri-reference",
    :source :ga4gh/gks-core},
   :InformationEntity
   {:inherits "gks-core:Entity",
    :maturity "trial use",
    :description
    "An abstract (non-physical) entity that represents 'information content' carried by physical or digital information artifacts such as books, web pages, data sets, or images.",
    :comment
    "As abstract, non-physical entities, Information Entities represent information independent of any specific language, format, or physical/digital medium. They can be simultaneously concretized in many distinct physical artifacts (e.g. all physical copies of the first edition of \"War and Peace\" carry the same information content, and thus concretize the same underlying Information Entity).",
    :heritableProperties
    {:specifiedBy
     {:oneOf
      [{:ref "#/$defs/Method"} {:refCurie "gks.core:iriReference"}],
      :description
      "A specification that describes all or part of the process that led to creation of the Information Entity",
      :comment
      "Examples: a specific experimental protocol or data analysis specification that describes how data were generated; an evidence interpretation guideline that describes steps taken to interpret data in making a variant pathogenicity classification; a method for using electron microscopy to image cell membrane proteins. Note that this attribute captures a specific *instance* of a specification or method (e.g. the specific electron microscopy method described in https://doi.org/10.1002/cpz1.1045) - as opposed to reporting a *type* of method applied (e.g. 'Transmission Electron Microscopy')."},
     :contributions
     {:type "array",
      :ordered false,
      :items {:ref "#/$defs/Contribution"},
      :description
      "Specific actions taken by an Agent toward the creation, modification, validation, or deprecation of an Information Entity.",
      :comment
      "This attribute holds one or more Contribution objects, which provide structured descriptions of a contribution made to the Information Entity by a particular agent."},
     :reportedIn
     {:type "array",
      :ordered false,
      :items
      {:oneOf
       [{:ref "#/$defs/Document"}
        {:refCurie "gks.core:iriReference"}]},
      :description
      "A document in which the the Information Entity is reported."}},
    :source :ga4gh/va-spec},
   :SystemicVariation
   {:inherits "Variation",
    :maturity "trial use",
    :description
    "A Variation of multiple molecules in the context of a system, e.g. a genome, sample, or homologous chromosomes.",
    :oneOf
    [{:ref "#/$defs/CopyNumberCount"}
     {:ref "#/$defs/CopyNumberChange"}],
    :discriminator {:propertyName "type"},
    :source :ga4gh/vrs},
   :DerivativeMolecule
   {:maturity "draft",
    :ga4gh {:prefix "DM", :inherent ["components"]},
    :inherits "MolecularVariation",
    :description
    "A molecule derived from segments of multiple adjoined molecular sequences, typically resulting from structural variation.",
    :type "object",
    :properties
    {:type
     {:extends "type",
      :const "DerivativeMolecule",
      :default "DerivativeMolecule",
      :description "MUST be \"DerivativeMolecule\"."},
     :components
     {:type "array",
      :uniqueItems false,
      :ordered true,
      :items
      {:oneOf
       [{:refCurie "gks.core:iriReference"}
        {:ref "#/$defs/Allele"}
        {:ref "#/$defs/CisPhasedBlock"}
        {:ref "#/$defs/Terminus"}
        {:ref "#/$defs/TraversalBlock"}]},
      :description
      "The molecular components that constitute the derivative molecule.",
      :minItems 2},
     :circular
     {:type "boolean",
      :description
      "A boolean indicating whether the molecule represented by the sequence is circular (true) or linear (false)."}},
    :required ["components"],
    :source :ga4gh/vrs},
   :CopyNumberChange
   {:maturity "draft",
    :ga4gh {:inherent ["location" "copyChange"], :prefix "CX"},
    :inherits "SystemicVariation",
    :type "object",
    :description
    "An assessment of the copy number of a :ref:`Location` within a system (e.g. genome, cell, etc.) relative to a baseline ploidy.",
    :properties
    {:type
     {:extends "type",
      :const "CopyNumberChange",
      :default "CopyNumberChange",
      :description "MUST be \"CopyNumberChange\""},
     :location
     {:oneOf
      [{:refCurie "gks.core:iriReference"} {:ref "#/$defs/Location"}],
      :description "The location of the subject of the copy change."},
     :copyChange
     {:type "string",
      :enum
      ["complete genomic loss"
       "high-level loss"
       "low-level loss"
       "loss"
       "regional base ploidy"
       "gain"
       "low-level gain"
       "high-level gain"],
      :description
      "MUST use one of the defined enumerations that are based on the corresponding EFO ontological terms for copy number variation. See Implementation Guidance for more details."}},
    :required ["location" "copyChange"],
    :source :ga4gh/vrs},
   :CopyChangeConstraint
   {:maturity "draft",
    :inherits "Constraint",
    :type "object",
    :description
    "The relative assessment of the change in copies that members of this categorical variant satisfies.",
    :properties
    {:type
     {:extends "type",
      :const "CopyChangeConstraint",
      :default "CopyChangeConstraint",
      :description "MUST be \"CopyChangeConstraint\""},
     :copyChange
     {:description
      "The relative assessment of the change in copies that members of this categorical variant satisfies.",
      :type "string",
      :enum
      ["complete genomic loss"
       "high-level loss"
       "low-level loss"
       "loss"
       "regional base ploidy"
       "gain"
       "low-level gain"
       "high-level gain"]}},
    :required ["copyChange"],
    :source :ga4gh/cat-vrs},
   :MolecularVariation
   {:inherits "Variation",
    :maturity "trial use",
    :description "A :ref:`variation` on a contiguous molecule.",
    :oneOf
    [{:ref "#/$defs/Allele"}
     {:ref "#/$defs/CisPhasedBlock"}
     {:ref "#/$defs/Adjacency"}
     {:ref "#/$defs/Terminus"}
     {:ref "#/$defs/DerivativeMolecule"}],
    :discriminator {:propertyName "type"},
    :source :ga4gh/vrs},
   :ClinicalVariantProposition
   {:inherits "SubjectVariantProposition",
    :maturity "trial use",
    :description
    "A proposition for use in describing the effect of variants in human subjects.",
    :heritableProperties
    {:geneContextQualifier
     {:description
      "Reports a gene impacted by the variant, which may contribute to the association described in the Proposition.",
      :comment
      "This is not a required field, and should be used when information about the gene context of the subject variant is known, relevant, and not apparent from the representation of the variant itself. It is useful for simpler variants such as SNVs where we cannot infer the gene context, but not necessary for complex variants like gene fusions, whose Cat-VRS representations reference specific transcripts and/or genes that provide a built-in gene context.",
      :oneOf
      [{:refCurie "gks.core:MappableConcept"}
       {:refCurie "gks.core:iriReference"}]},
     :alleleOriginQualifier
     {:description
      "Reports whether the Proposition should be interpreted in the context of an inherited (germline) variant, an acquired (somatic) mutation, or another more nuanced concept. Consider using terms or codes from community terminologies here, e.g. terms from the 'allele origin' branch of the GENO ontology such as GENO:0000882 (somatic allele origin).",
      :oneOf
      [{:refCurie "gks.core:MappableConcept"}
       {:refCurie "gks.core:iriReference"}]}},
    :source :ga4gh/va-spec},
   :Allele
   {:maturity "trial use",
    :ga4gh {:prefix "VA", :inherent ["location" "state"]},
    :inherits "MolecularVariation",
    :description "The state of a molecule at a :ref:`Location`.",
    :type "object",
    :properties
    {:type
     {:extends "type",
      :const "Allele",
      :default "Allele",
      :description "MUST be \"Allele\""},
     :location
     {:oneOf
      [{:refCurie "gks.core:iriReference"} {:ref "#/$defs/Location"}],
      :description "The location of the Allele"},
     :state
     {:ref "#/$defs/SequenceExpression",
      :description "An expression of the sequence state"}},
    :required ["location" "state"],
    :source :ga4gh/vrs},
   :Expression
   {:inherits "gks-core:Element",
    :type "object",
    :maturity "trial use",
    :description
    "Representation of a variation by a specified nomenclature or syntax for a Variation object. Common examples of expressions for the description of molecular variation include the HGVS and ISCN nomenclatures.",
    :properties
    {:syntax
     {:type "string",
      :enum
      ["hgvs.c"
       "hgvs.p"
       "hgvs.g"
       "hgvs.m"
       "hgvs.n"
       "hgvs.r"
       "iscn"
       "gnomad"
       "spdi"],
      :description
      "The syntax used to describe the variation. The value should be one of the supported syntaxes."},
     :value
     {:type "string",
      :description
      "The expression of the variation in the specified syntax. The value should be a valid expression in the specified syntax."},
     :syntax_version
     {:type "string",
      :description
      "The version of the syntax used to describe the variation. This is particularly important for HGVS expressions, as the syntax has evolved over time."}},
    :required ["syntax" "value"],
    :source :ga4gh/vrs},
   :DefiningLocationConstraint
   {:maturity "trial use",
    :type "object",
    :inherits "Constraint",
    :description
    "The defining location and its associated relationships that are congruent with member locations.",
    :properties
    {:type
     {:extends "type",
      :const "DefiningLocationConstraint",
      :default "DefiningLocationConstraint",
      :description "MUST be \"DefiningLocationConstraint\""},
     :location
     {:oneOf
      [{:refCurie "vrs:SequenceLocation"}
       {:refCurie "gks.core:iriReference"}]},
     :relations
     {:type "array",
      :ordered false,
      :items {:refCurie "gks.core:MappableConcept"},
      :description
      "Defined relationships from which members relate to the defining location."},
     :matchCharacteristic
     {:refCurie "gks.core:MappableConcept",
      :description
      "A characteristic of the location that is used to match the defining location to member locations."}},
    :required ["location" "matchCharacteristic"],
    :source :ga4gh/cat-vrs},
   :CopyCountConstraint
   {:maturity "trial use",
    :inherits "Constraint",
    :type "object",
    :description
    "The exact or range of copies that members of this categorical variant must satisfy.",
    :properties
    {:type
     {:extends "type",
      :const "CopyCountConstraint",
      :default "CopyCountConstraint",
      :description "MUST be \"CopyCountConstraint\""},
     :copies
     {:description
      "The precise value or range of copies members of this categorical variant must satisfy.",
      :oneOf [{:type "integer"} {:refCurie "vrs:Range"}]}},
    :required ["copies"],
    :source :ga4gh/cat-vrs},
   :VariantDiagnosticProposition
   {:inherits "ClinicalVariantProposition",
    :type "object",
    :maturity "trial use",
    :description
    "A Proposition about whether a variant is associated with a disease (a diagnostic inclusion criterion), or absence of a disease (diagnostic exclusion criterion).",
    :properties
    {:type
     {:extends "type",
      :const "VariantDiagnosticProposition",
      :default "VariantDiagnosticProposition",
      :description "MUST be \"VariantDiagnosticProposition\"."},
     :predicate
     {:extends "predicate",
      :enum
      ["isDiagnosticInclusionCriterionFor"
       "isDiagnosticExclusionCriterionFor"]},
     :objectCondition
     {:extends "object",
      :oneOf [{:ref "Condition"} {:refCurie "gks.core:iriReference"}],
      :description "The disease that is evaluated for diagnosis."}},
    :required ["predicate" "objectCondition"],
    :source :ga4gh/va-spec},
   :Extension
   {:inherits "Element",
    :type "object",
    :maturity "trial use",
    :description
    "The Extension class provides entities with a means to include additional attributes that are outside of the specified standard but needed by a given content provider or system implementer. These extensions are not expected to be natively understood, but may be used for pre-negotiated exchange of message attributes between systems.",
    :properties
    {:name
     {:type "string",
      :description
      "A name for the Extension. Should be indicative of its meaning and/or the type of information it value represents."},
     :value
     {:type ["number" "string" "boolean" "object" "array" "null"],
      :description
      "The value of the Extension - can be any primitive or structured object",
      :additionalProperties true},
     :description
     {:type "string",
      :description
      "A description of the meaning or utility of the Extension, to explain the type of information it is meant to hold."}},
    :required ["name" "value"],
    :source :ga4gh/gks-core},
   :MappableConcept
   {:inherits "Element",
    :type "object",
    :maturity "trial use",
    :description
    "A concept based on a primaryCoding and/or name that may be mapped to one or more other :ref:`Codings <Coding>`.",
    :properties
    {:conceptType
     {:type "string",
      :description
      "A term indicating the type of concept being represented by the MappableConcept.",
      :comment
      "This attribute can be used to indicate the type of concept being represented by the MappableConcept. For example, in a variant pathogenicity assessment, the conceptType could be 'Pathogenicity Strength', 'Disease', 'Phenotype', 'Drug', 'Tumor Type', 'Evidence Direction', 'Evidence Strength', etc."},
     :name
     {:type "string", :description "A primary name for the concept."},
     :primaryCoding
     {:ref "#/$defs/Coding",
      :description "A primary coding for the concept."},
     :mappings
     {:type "array",
      :ordered false,
      :items {:ref "#/$defs/ConceptMapping"},
      :description
      "A list of mappings to concepts in terminologies or code systems. Each mapping should include a coding and a relation."}},
    :anyOf [{:required ["name"]} {:required ["primaryCoding"]}],
    :source :ga4gh/gks-core},
   :Terminus
   {:maturity "draft",
    :ga4gh {:prefix "TM", :inherent ["location"]},
    :inherits "MolecularVariation",
    :description
    "The `Terminus` data class provides a structure for describing the end (terminus) of a molecule. Structurally similar to Adjacency, but used for describing where a molecule terminates (instead of adjoining another molecule).",
    :type "object",
    :properties
    {:type
     {:extends "type",
      :const "Terminus",
      :default "Terminus",
      :description "MUST be \"Terminus\"."},
     :location
     {:oneOf
      [{:refCurie "gks.core:iriReference"} {:ref "#/$defs/Location"}],
      :description "The location of the terminus."}},
    :required ["location"],
    :source :ga4gh/vrs},
   :Range
   {:maturity "trial use",
    :description
    "An inclusive range of values bounded by one or more integers.",
    :type "array",
    :ordered true,
    :items {:oneOf [{:type "integer"} {:type "null"}]},
    :maxItems 2,
    :minItems 2,
    :source :ga4gh/vrs},
   :Contribution
   {:type "object",
    :inherits "gks-core:Entity",
    :maturity "trial use",
    :description
    "An action taken by an agent in contributing to the creation, modification, assessment, or deprecation of a particular entity (e.g. a Statement, EvidenceLine, DataSet, Publication, etc.)",
    :properties
    {:type
     {:extends "type",
      :const "Contribution",
      :default "Contribution",
      :description "MUST be \"Contribution\"."},
     :contributor
     {:ref "#/$defs/Agent",
      :description "The agent that made the contribution."},
     :activityType
     {:refCurie "gks.core:MappableConcept",
      :description
      "The specific type of activity performed or role played by an agent in making the contribution (e.g. for a publication, agents may contribute as a primary author, editor, figure designer, data generator, etc.). Values of this property may be framed as activities, or as contribution roles (e.g. using terms from the Contribution Role Ontology (CRO))."},
     :date
     {:refCurie "gks.core:datetime",
      :description "When the contributing activity was completed.",
      :comment
      "Populate this attribute with the date and time the activity was completed. MUST use ISO8601 format (\"YYYY-MM-DDTHH:MM:SS\"), e.g. \"2024-02-25T09:25:00\". In cases where a date alone is sufficient, the \"THH:MM:SS\" portion of the datetime string MAY be omitted (e.g. \"2024-02-25\").  Specifying a time zone is optional, and SHOULD use the Timezone Offset convention (+/- hours offset from UTC), e.g. \"2024-02-25T09:25:00-5:00\" for the US eastern time zone."}},
    :source :ga4gh/va-spec},
   :FeatureContextConstraint
   {:maturity "draft",
    :type "object",
    :inherits "Constraint",
    :description
    "The feature that members of this categorical variant are associated with.",
    :properties
    {:type
     {:extends "type",
      :const "FeatureContextConstraint",
      :default "FeatureContextConstraint",
      :description "MUST be \"FeatureContextConstraint\""},
     :featureContext
     {:description "A feature identifier.",
      :refCurie "gks.core:MappableConcept"}},
    :required ["featureContext"],
    :source :ga4gh/cat-vrs},
   :VariantTherapeuticResponseProposition
   {:inherits "ClinicalVariantProposition",
    :maturity "trial use",
    :type "object",
    :description
    "A Proposition about the role of a variant in modulating the response of a neoplasm to drug administration or other therapeutic procedures.",
    :properties
    {:type
     {:extends "type",
      :const "VariantTherapeuticResponseProposition",
      :default "VariantTherapeuticResponseProposition",
      :description "MUST be \"VariantTherapeuticResponseProposition\"."},
     :predicate
     {:extends "predicate",
      :enum ["predictsSensitivityTo" "predictsResistanceTo"]},
     :objectTherapeutic
     {:description
      "A drug administration or other therapeutic procedure that the neoplasm is intended to respond to.",
      :extends "object",
      :oneOf
      [{:ref "Therapeutic"} {:refCurie "gks.core:iriReference"}]},
     :conditionQualifier
     {:oneOf [{:ref "Condition"} {:refCurie "gks.core:iriReference"}],
      :description
      "Reports the disease context in which the variant's association with therapeutic sensitivity or resistance is evaluated. Note that this is a required qualifier in therapeutic response propositions."}},
    :required ["predicate" "objectTherapeutic" "conditionQualifier"],
    :source :ga4gh/va-spec},
   :VariantPrognosticProposition
   {:inherits "ClinicalVariantProposition",
    :type "object",
    :maturity "trial use",
    :description
    "A Proposition about whether a variant is associated with an improved or worse outcome for a disease.",
    :properties
    {:type
     {:extends "type",
      :const "VariantPrognosticProposition",
      :default "VariantPrognosticProposition",
      :description "MUST be \"VariantPrognosticProposition\"."},
     :predicate
     {:extends "predicate",
      :enum
      ["associatedWithBetterOutcomeFor"
       "associatedWithWorseOutcomeFor"]},
     :objectCondition
     {:extends "object",
      :oneOf [{:ref "Condition"} {:refCurie "gks.core:iriReference"}],
      :description "The disease that is evaluated for outcome."}},
    :required ["predicate" "objectCondition"],
    :source :ga4gh/va-spec},
   :code
   {:type "string",
    :maturity "trial use",
    :description
    "Indicates that the value is taken from a set of controlled strings defined elsewhere. Technically, a code is restricted to a string which has at least one character and no leading or trailing whitespace, and where there is no whitespace other than single spaces in the contents.",
    :pattern "\\S+( \\S+)*",
    :example "ENSG00000139618",
    :source :ga4gh/gks-core},
   :datetime
   {:type "string",
    :maturity "trial use",
    :description
    "A string is valid against this format if it represents a date-time in the following format: YYYY:MM::DDThh:mm:ss.sTZD.",
    :format "date-time",
    :source :ga4gh/gks-core},
   :Ga4ghIdentifiableObject
   {:maturity "trial use",
    :inherits "gks-core:Entity",
    :description
    "An object for which a GA4GH computed identifier can be created.",
    :ga4gh {:inherent ["type"]},
    :heritableProperties
    {:type {:type "string"},
     :digest
     {:description
      "A sha512t24u digest created using the VRS Computed Identifier algorithm.",
      :type "string",
      :pattern "^[0-9A-Za-z_\\-]{32}$"}},
    :source :ga4gh/vrs},
   :Coding
   {:inherits "Element",
    :type "object",
    :maturity "trial use",
    :description
    "A structured representation of a code for a defined concept in a terminology or code system.",
    :properties
    {:name
     {:type "string",
      :description
      "The human-readable name for the coded concept, as defined by the code system."},
     :system
     {:type "string",
      :description
      "The terminology/code system that defined the code. May be reported as a free-text name (e.g. 'Sequence Ontology'), but it is preferable to provide a uri/url for the system."},
     :systemVersion
     {:type "string",
      :description
      "Version of the terminology or code system that provided the code."},
     :code
     {:ref "#/$defs/code",
      :description
      "A symbol uniquely identifying the concept, as in a syntax defined by the code system. e.g. 'civic.did:30', 'MONDO:005061', and 'C3512' are codes defined by different systems for representing the concept of 'lung adenocarcinoma'.  If a dereferencable identifier is available for the code, it should be provided in the `iris` field."},
     :iris
     {:type "array",
      :ordered false,
      :items {:ref "#/$defs/iriReference"},
      :description
      "A list of IRIs that are associated with the coding. This can be used to provide additional context or to link to additional information about the concept."}},
    :required ["system" "code"],
    :source :ga4gh/gks-core},
   :CategoricalVariant
   {:type "object",
    :inherits "gks-core:Entity",
    :maturity "trial use",
    :description
    "A representation of a categorically-defined domain for variation, in which individual Constraintual variation instances may be members of the domain.",
    :properties
    {:type
     {:extends "type",
      :const "CategoricalVariant",
      :default "CategoricalVariant",
      :description "MUST be \"CategoricalVariant\""},
     :members
     {:type "array",
      :ordered false,
      :description
      "A non-exhaustive list of VRS Variations that satisfy the constraints of this categorical variant.",
      :items
      {:oneOf
       [{:refCurie "vrs:Variation"}
        {:refCurie "gks.core:iriReference"}]}},
     :constraints
     {:type "array",
      :ordered false,
      :items {:ref "#/$defs/Constraint"}},
     :mappings
     {:type "array",
      :ordered false,
      :items {:refCurie "gks.core:ConceptMapping"},
      :description
      "A list of mappings to concepts in terminologies or code systems. Each mapping should include a coding and a relation."}},
    :source :ga4gh/cat-vrs},
   :CisPhasedBlock
   {:maturity "trial use",
    :ga4gh {:prefix "CPB", :inherent ["members"]},
    :inherits "MolecularVariation",
    :description
    "An ordered set of co-occurring :ref:`variants <Variation>` on the same molecule.",
    :type "object",
    :properties
    {:type
     {:extends "type",
      :const "CisPhasedBlock",
      :default "CisPhasedBlock",
      :description "MUST be \"CisPhasedBlock\""},
     :members
     {:type "array",
      :ordered false,
      :minItems 2,
      :uniqueItems false,
      :items
      {:oneOf
       [{:ref "#/$defs/Allele"} {:refCurie "gks.core:iriReference"}]},
      :description
      "A list of :ref:`Alleles <Allele>` that are found in-cis on a shared molecule."},
     :sequenceReference
     {:ref "#/$defs/SequenceReference",
      :description
      "An optional Sequence Reference on which all of the in-cis Alleles are found. When defined, this may be used to implicitly define the `sequenceReference` attribute for each of the CisPhasedBlock member Alleles."}},
    :required ["members"],
    :source :ga4gh/vrs},
   :Method
   {:type "object",
    :inherits "gks-core:Entity",
    :maturity "trial use",
    :description
    "A set of instructions that specify how to achieve some objective.",
    :comment
    "In scientific research, these include things like experimental protocols, study designs, data analysis parameters, curation guidelines, cohort selection criteria, and rule sets.",
    :properties
    {:type
     {:extends "type",
      :const "Method",
      :default "Method",
      :description "MUST be \"Method\"."},
     :subtype
     {:refCurie "gks.core:MappableConcept",
      :description
      "A specific type of method that a Method instance represents (e.g. 'Variant Interpretation Guideline', or 'Experimental Protocol').",
      :comment
      "This attribute can be used to report a specific type for the Method, in cases where a model does not define Method subclasses for this purpose.  Implementers can define their own set of method type codes/terms, to match the needs of their application."},
     :reportedIn
     {:oneOf
      [{:ref "#/$defs/Document"} {:refCurie "gks.core:iriReference"}],
      :description "A document in which the the Method is reported."}},
    :source :ga4gh/va-spec},
   :StudyResult
   {:inherits "InformationEntity",
    :maturity "trial use",
    :description
    "A collection of data items from a single study that pertain to a particular subject or experimental unit in the study, along with optional provenance information describing how these data items were generated.",
    :comment
    "Note that a 'dataItem' attribute, which would hold an array of data selected for inclusion in the StudyResult, is not included in this version of the StudyResult class. Profiles that extend this core class can define data type specific attributes to capture such information (e.g.'focusAlleleCount', 'focusAlleleFrequency', and 'locusAlleleCount' attributes in a CohortAlleleFrequencyStudyResult profile). But technical limitations in our current profiling framework tooling forced us to omit a core 'dataItems attribute that these specialized attributes would conceptually extend.",
    :oneOf
    [{:ref "#/$defs/CohortAlleleFrequencyStudyResult"}
     {:ref "#/$defs/ExperimentalVariantFunctionalImpactStudyResult"}],
    :heritableProperties
    {:focus
     {:oneOf
      [{:refCurie "gks.core:Entity"}
       {:refCurie "gks.core:MappableConcept"}
       {:refCurie "gks.core:iriReference"}],
      :description
      "The specific participant, subject or experimental unit in a Study that data included in the StudyResult object is about - e.g. a particular variant in a population allele frequency dataset like ExAC or gnomAD.",
      :comment
      "The 'focus' of a StudyResult is the what anchors selection of all data items and provenance information that it contains. This focus may be a single participant or subject of a study (e.g. one patient in a clinical study, or one allele in a population frequency analysis). It may be a study group defined as an experimental unit in the study (e.g. a specific treatment arm of a clinical study). Or it may be a variable defined as a unit of analysis in the study (e.g. 'exposure to nicotine' in an analysis correlating this variable with clinical outcomes)."},
     :sourceDataSet
     {:ref "#/$defs/DataSet",
      :description
      "A larger DataSet from which the data included in the StudyResult was taken or derived.",
      :comment
      "In most cases, a StudyResult will be generated using data from one source dataset - but it is possible multiple datasets related to a single study contain data about a particular focus that get collected into a single StudyResult instance."},
     :ancillaryResults
     {:maturity "draft",
      :description
      "An object in which implementers can define custom fields to capture additional results derived from analysis of primary data items captured in standard attributes in the main body of the Study Result. e.g. in a Cohort Allele Frequency Study Result, this maybe a grpMaxFAF95 calculation, or homozygote/heterozygote calls derived from analyzing raw allele count data.",
      :comment
      "This field is left at draft maturity, as it is different from most GKS objects by allowing any content to be added.",
      :type "object",
      :additionalProperties true},
     :qualityMeasures
     {:maturity "draft",
      :description
      "An object in which implementers can define custom fields to capture metadata about the quality/provenance of the primary data items captured in standard attributes in the main body of the Study Result. e.g. a sequencing coverage metric in a Cohort Allele Frequency Study Result.",
      :comment
      "This field is left at draft maturity, as it is different from most GKS objects by allowing any content to be added.",
      :type "object",
      :additionalProperties true}},
    :heritableRequired ["focus"],
    :source :ga4gh/va-spec},
   :Adjacency
   {:maturity "trial use",
    :ga4gh {:prefix "AJ", :inherent ["adjoinedSequences" "linker"]},
    :inherits "MolecularVariation",
    :description
    "The `Adjacency` class represents the adjoining of the end of a sequence with the beginning of an adjacent sequence, potentially with an intervening linker sequence.",
    :type "object",
    :properties
    {:type
     {:extends "type",
      :const "Adjacency",
      :default "Adjacency",
      :description "MUST be \"Adjacency\"."},
     :adjoinedSequences
     {:type "array",
      :uniqueItems false,
      :ordered true,
      :items
      {:oneOf
       [{:refCurie "gks.core:iriReference"} {:ref "#/$defs/Location"}],
       :not {:required ["start" "end"]}},
      :description
      "The terminal sequence or pair of adjoined sequences that defines in the adjacency.",
      :minItems 2,
      :maxItems 2},
     :linker
     {:ref "#/$defs/SequenceExpression",
      :description "The sequence found between adjoined sequences."},
     :homology
     {:type "boolean",
      :maturity "draft",
      :description
      "A flag indicating if coordinate ambiguity in the adjoined sequences is from sequence homology (true) or other uncertainty, such as instrument ambiguity (false)."}},
    :required ["adjoinedSequences"],
    :source :ga4gh/vrs},
   :CohortAlleleFrequencyStudyResult
   {:inherits "StudyResult",
    :maturity "trial use",
    :type "object",
    :description
    "A StudyResult that reports measures related to the frequency of an Allele in a cohort",
    :properties
    {:type
     {:extends "type",
      :const "CohortAlleleFrequencyStudyResult",
      :default "CohortAlleleFrequencyStudyResult",
      :description "MUST be \"CohortAlleleFrequencyStudyResult\"."},
     :sourceDataSet
     {:extends "sourceDataSet",
      :description
      "The dataset from which the CohortAlleleFrequencyStudyResult was reported."},
     :focusAllele
     {:extends "focus",
      :description
      "The Allele for which frequency results are reported.",
      :oneOf
      [{:refCurie "vrs:Allele"} {:refCurie "gks.core:iriReference"}]},
     :focusAlleleCount
     {:type "integer",
      :description
      "The number of occurrences of the focusAllele in the cohort."},
     :locusAlleleCount
     {:type "integer",
      :description
      "The number of occurrences of all alleles at the locus in the cohort."},
     :focusAlleleFrequency
     {:type "number",
      :description "The frequency of the focusAllele in the cohort."},
     :cohort
     {:ref "#/$defs/StudyGroup",
      :description "The cohort from which the frequency was derived."},
     :subCohortFrequency
     {:type "array",
      :ordered false,
      :description
      "A list of CohortAlleleFrequency objects describing subcohorts of the cohort currently being described. Subcohorts can be further subdivided into more subcohorts. This enables, for example, the description of different ancestry groups and sexes among those ancestry groups.",
      :items {:ref "#/$defs/CohortAlleleFrequencyStudyResult"}}},
    :required
    ["focusAllele"
     "focusAlleleCount"
     "locusAlleleCount"
     "focusAlleleFrequency"
     "cohort"],
    :source :ga4gh/va-spec},
   :CopyNumberCount
   {:maturity "trial use",
    :ga4gh {:inherent ["location" "copies"], :prefix "CN"},
    :inherits "SystemicVariation",
    :type "object",
    :description
    "The absolute count of discrete copies of a :ref:`Location` within a system (e.g. genome, cell, etc.).",
    :properties
    {:type
     {:extends "type",
      :const "CopyNumberCount",
      :default "CopyNumberCount",
      :description "MUST be \"CopyNumberCount\""},
     :location
     {:oneOf
      [{:refCurie "gks.core:iriReference"} {:ref "#/$defs/Location"}],
      :description "The location of the subject of the copy count."},
     :copies
     {:oneOf [{:type "integer"} {:ref "#/$defs/Range"}],
      :description
      "The integral number of copies of the subject in a system"}},
    :required ["location" "copies"],
    :source :ga4gh/vrs},
   :SequenceReference
   {:maturity "trial use",
    :inherits "gks-core:Entity",
    :ga4gh {:inherent ["refgetAccession" "type"]},
    :type "object",
    :description "A sequence of nucleic or amino acid character codes.",
    :properties
    {:type
     {:extends "type",
      :const "SequenceReference",
      :default "SequenceReference",
      :description "MUST be \"SequenceReference\""},
     :refgetAccession
     {:description
      "A `GA4GH RefGet <http://samtools.github.io/hts-specs/refget.html>`_ identifier for the referenced sequence, using the sha512t24u digest.",
      :type "string",
      :pattern "^SQ.[0-9A-Za-z_\\-]{32}$"},
     :residueAlphabet
     {:type "string",
      :description
      "The interpretation of the character codes referred to by the refget accession, where \"aa\" specifies an amino acid character set, and \"na\" specifies a nucleic acid character set.",
      :enum ["aa" "na"]},
     :sequence
     {:ref "#/$defs/sequenceString",
      :description
      "A :ref:`sequenceString` that is a literal representation of the referenced sequence."},
     :moleculeType
     {:type "string",
      :description
      "Molecule types as `defined by RefSeq <https://www.ncbi.nlm.nih.gov/books/NBK21091/>`_ (see Table 1). MUST be one of \"genomic\", \"RNA\", \"mRNA\", or \"protein\".",
      :enum ["genomic" "RNA" "mRNA" "protein"]},
     :circular
     {:type "boolean",
      :description
      "A boolean indicating whether the molecule represented by the sequence is circular (true) or linear (false)."}},
    :required ["refgetAccession"],
    :source :ga4gh/vrs},
   :Location
   {:inherits "Ga4ghIdentifiableObject",
    :maturity "trial use",
    :description "A contiguous segment of a biological sequence.",
    :oneOf [{:ref "#/$defs/SequenceLocation"}],
    :discriminator {:propertyName "type"},
    :source :ga4gh/vrs},
   :Statement
   {:type "object",
    :inherits "InformationEntity",
    :maturity "trial use",
    :description
    "A claim of purported truth as made by a particular agent, on a particular occasion. Statements may be used to put forth a possible fact (i.e. a 'Proposition') as true or false, or to provide a more nuanced assessment of the level of confidence or evidence supporting a particular Proposition.",
    :comment
    "The model supports two \"modes of use\" for Statements, which differ in what they say about their SPOQ Proposition, and can be distinguished by whether 'direction' and 'strength' or 'score' attributes are populated.\nIn \"Assertion Mode\", a Statement simply reports an SPOQ proposition to be true or false (e.g. that \"BRCA2 c.8023A>G is pathogenic for Breast Cancer\"). The 'strength' and 'score' attributes are not populated, and 'direction' is assumed true/supports if not otherwise indicated. This mode is used by project reporting conclusive assertions about a domain of discourse, but not providing confidence or evidence level assessments.\nIn \"Proposition Assessment Mode\", a Statement describes the overall state of evidence and/or confidence surrounding the SPOQ Proposition - which is not necessarily being asserted as true. The 'direction' and 'strength' or 'score' attributes are populated, which allows for Statements to report things like \"there is weak evidence supporting the proposition that `BRCA2 c.8023A>G is causal for Breast Cancer`\", or \"we have high confidence that the proposition `PAH:c.1285C>A is causal for Phenylketonuria` is false\"). This mode is used in projects to track the evolving state of support for propositions of interest, as curators actively collect evidence and work toward a conclusive assertion.",
    :properties
    {:type
     {:extends "type",
      :const "Statement",
      :default "Statement",
      :description "MUST be \"Statement\"."},
     :proposition
     {:ref "#/$defs/Proposition",
      :description
      "A possible fact, the validity of which is assessed and reported by the Statement. A Statement can put forth the proposition as being true, false, or uncertain, and may provide an assessment of the level of confidence/evidence supporting this claim.",
      :comment
      "Statements put forth a Proposition that expresses some possible fact about the world, and may provide an assessment of this proposition’s validity (i.e. how likely it is to be true or false based on evaluated evidence). The semantics of the Proposition are explicitly captured using subject, predicate, object, and optional qualifier attributes (SPOQ). An assessment of the Proposition’s validity can optionally be captured using the Statement's direction, strength, and/or score attributes (DS)."},
     :direction
     {:description
      "A term indicating whether the Statement supports, disputes, or remains neutral w.r.t. the validity of the Proposition it evaluates.",
      :comment
      "Statements put forth a Proposition that expresses some possible fact about the world, and may provide an assessment of this proposition's validity (i.e. how likely it is to be true or false based on evaluated evidence) using its 'direction', 'strength', and 'score' attributes. The 'direction' attribute is used to indicate whether the Statement's Proposition is *supported* by the agent's assessment (when evidence favors its validity), is *disputed* by the agent's assessment (when evidence argues against its validity), or remains *neutral* (when conflicting or insufficient evidence exists to assert one direction or the other).",
      :type "string",
      :enum ["supports" "neutral" "disputes"]},
     :strength
     {:description
      "A term used to report the strength of a Proposition's assessment in the direction indicated (i.e. how strongly supported or disputed the Proposition is believed to be).  Implementers may choose to frame a strength assessment in terms of how *confident* an agent is that the Proposition is true or false, or in terms of the *strength of all evidence* they believe supports or disputes it.",
      :comment
      "Statements put forth a Proposition that expresses some possible fact about the world, and may provide an assessment of this proposition's validity (i.e. how likely it is to be true or false based on evaluated evidence) using 'direction', 'strength', and 'score' attributes. The 'strength' attribute is used to report the strength of this assessment in the direction indicated. Strength can be framed as a *level of confidence* that the Proposition is true or false, or as a *level of evidence* that supports or disputes it. Data creators can define the permissible values for the 'strength' attribute to indicate which of these facets is being assessed (e.g. 'high confidence' vs 'low confidence', or 'strong evidence' vs 'weak evidence') - or they can choose values that don't commit to one or the other if they don't want to make the distinction (e.g. 'high' vs 'medium' vs 'low').",
      :refCurie "gks.core:MappableConcept"},
     :score
     {:type "number",
      :maturity "draft",
      :description
      "A quantitative score that indicates the strength of a Proposition's assessment in the direction indicated (i.e. how strongly supported or disputed the Proposition is believed to be). Depending on its implementation, a score may reflect how *confident* that agent is that the Proposition is true or false, or the *strength of evidence* they believe supports or disputes it. Instructions for how to interpret the meaning of a given score may be gleaned from the method or document referenced in 'specifiedBy' attribute.",
      :comment
      "The 'score' attribute serves the same purpose as 'strength', but allows for a quantitative assessment based on a numerical score."},
     :classification
     {:refCurie "gks.core:MappableConcept",
      :description
      "A single term or phrase summarizing the outcome of direction and strength assessments of a Statement's Proposition, in terms of a classification of its subject.",
      :comment
      "Permissible values for this attribute are typically selected to be succinct and familiar in the target community of practice - and can be provided to report of a statement's conclusion in user-friendly terms. For example, in a Statement assessing the proposition that \"BRCA2 c.8023A>G is pathogenic for Breast Cancer\", and reporting a direction of 'supports' and strength of 'likely', the term  'likely pathogenic' from the ACMG Variant Interpretation Guidelines would be used as a subject classification."},
     :hasEvidenceLines
     {:type "array",
      :ordered false,
      :items
      {:anyOf
       [{:ref "#/$defs/EvidenceLine"}
        {:refCurie "gks.core:iriReference"}]},
      :description
      "An evidence-based argument that supports or disputes the validity of the proposition that a Statement assesses or puts forth as true. The strength and direction of this argument (whether it supports or disputes the proposition, and how strongly) is based on an interpretation of one or more pieces of information as evidence (i.e. 'Evidence Items).",
      :comment
      "Evidence Lines result from the interpretation of one or more pieces of information to build an argument for or against a particular Proposition. These arguments have direction (supporting / disputing) and strength (e.g. strong, moderate, weak) relative to the Proposition they are evaluated against. For example, ExaC allele counts and frequency calculations for the BRCA2 c.8023A>G variant in different populations may be collectively assessed to provide an argument of 'moderate' strength in 'support of ' a proposition that the variant is pathogenicity for breast cancer. Evidence Lines are useful in cases where a data provider wants to describe in detail how information was assessed as evidence to generate and score different arguments for or against a Statement's Proposition. Evidence Lines can be omitted if such information is not available or needed."}},
    :required ["proposition" "direction"],
    :source :ga4gh/va-spec},
   :DefiningAlleleConstraint
   {:maturity "trial use",
    :type "object",
    :inherits "Constraint",
    :description
    "The defining allele and its associated relationships that are congruent with member variants.",
    :properties
    {:type
     {:extends "type",
      :const "DefiningAlleleConstraint",
      :default "DefiningAlleleConstraint",
      :description "MUST be \"DefiningAlleleConstraint\""},
     :allele
     {:oneOf
      [{:refCurie "vrs:Allele"} {:refCurie "gks.core:iriReference"}]},
     :relations
     {:type "array",
      :ordered false,
      :items {:refCurie "gks.core:MappableConcept"},
      :description
      "Defined relationships from which members relate to the defining allele."}},
    :required ["allele"],
    :source :ga4gh/cat-vrs},
   :ExperimentalVariantFunctionalImpactProposition
   {:inherits "SubjectVariantProposition",
    :maturity "trial use",
    :type "object",
    :description
    "A Proposition describing the impact of a variant on the function sequence feature (typically a gene or gene product).",
    :properties
    {:type
     {:extends "type",
      :const "ExperimentalVariantFunctionalImpactProposition",
      :default "ExperimentalVariantFunctionalImpactProposition",
      :description
      "MUST be \"ExperimentalVariantFunctionalImpactProposition\"."},
     :predicate
     {:extends "predicate",
      :description
      "The relationship the Proposition describes between the subject variant and object sequence feature whose function it may alter.",
      :default "impactsFunctionOf"},
     :objectSequenceFeature
     {:extends "object",
      :oneOf
      [{:refCurie "gks.core:iriReference"}
       {:refCurie "gks.core:MappableConcept"}],
      :description
      "The sequence feature (typically a gene or gene product) on whose function the impact of the subject variant is reported."},
     :experimentalContextQualifier
     {:description
      "An assay in which the reported variant functional impact was determined - providing a specific experimental context in which this effect is asserted to hold.",
      :anyOf
      [{:refCurie "gks.core:iriReference"}
       {:ref "#/$defs/Document"}
       {:type "object",
        :additionalProperties true,
        :comment
        "It is expected that non-Document objects will conform to externally-maintained community schemas for representing experimental context data."}]}},
    :required ["predicate" "objectSequenceFeature"],
    :source :ga4gh/va-spec},
   :Agent
   {:type "object",
    :inherits "gks-core:Entity",
    :maturity "trial use",
    :description
    "An autonomous actor (person, organization, or software agent) that bears some form of responsibility for an activity taking place, for the existence of an entity, or for another agent's activity.",
    :properties
    {:type
     {:extends "type",
      :const "Agent",
      :default "Agent",
      :description "MUST be \"Agent\"."},
     :name
     {:type "string",
      :extends "name",
      :description "The given name of the Agent."},
     :subtype
     {:refCurie "gks.core:MappableConcept",
      :description
      "A specific type of agent the Agent object represents. Recommended subtypes include codes for `person`, `organization`, or `software`."}},
    :source :ga4gh/va-spec},
   :SequenceLocation
   {:maturity "trial use",
    :ga4gh {:inherent ["start" "end" "sequenceReference"], :prefix "SL"},
    :inherits "Location",
    :description
    "A :ref:`Location` defined by an interval on a :ref:`SequenceReference`.",
    :type "object",
    :properties
    {:type
     {:extends "type",
      :const "SequenceLocation",
      :default "SequenceLocation",
      :description "MUST be \"SequenceLocation\""},
     :sequenceReference
     {:oneOf
      [{:refCurie "gks.core:iriReference"}
       {:ref "#/$defs/SequenceReference"}],
      :description
      "A reference to a :ref:`SequenceReference` on which the location is defined."},
     :start
     {:oneOf [{:type "integer"} {:ref "#/$defs/Range"}],
      :description
      "The start coordinate or range of the SequenceLocation. The minimum value of this coordinate or range is 0. For locations on linear sequences, this MUST represent a coordinate or range less than or equal to the value of `end`. For circular sequences, `start` is greater than `end` when the location spans the sequence 0 coordinate."},
     :end
     {:oneOf [{:type "integer"} {:ref "#/$defs/Range"}],
      :description
      "The end coordinate or range of the SequenceLocation. The minimum value of this coordinate or range is 0. For locations on linear sequences, this MUST represent a coordinate or range greater than or equal to the value of `start`. For circular sequences, `end` is less than `start` when the location spans the sequence 0 coordinate."},
     :sequence
     {:description
      "The literal sequence encoded by the `sequenceReference` at these coordinates.",
      :ref "#/$defs/sequenceString"}},
    :source :ga4gh/vrs},
   :Document
   {:type "object",
    :inherits "gks-core:Entity",
    :maturity "trial use",
    :description
    "A collection of information, usually in a text-based or graphic human-readable form, intended to be read and understood together as a whole.",
    :comment
    "This is currently specified as an Entity, instead of an InformationEntity. This may be reopened for discussion when use cases demonstrate the need to treat Documents in the same way that information inside Documents is treated.",
    :properties
    {:type
     {:extends "type",
      :const "Document",
      :default "Document",
      :description "Must be \"Document\""},
     :subtype
     {:maturity "draft",
      :refCurie "gks.core:MappableConcept",
      :description
      "A specific type of document that a Document instance represents (e.g.  'publication', 'patent', 'pathology report')",
      :comment
      "This attribute can be used to report a specific type for the Document, in cases where a model does not define Document subclasses for this purpose.  Implementers can define their own set of document type codes/terms, to match the needs of their application."},
     :title
     {:type "string",
      :description
      "The official title given to the document by its authors."},
     :urls
     {:type "array",
      :ordered false,
      :items
      {:type "string", :format "uri", :pattern "^(https?|s?ftp)://"},
      :description
      "One or more URLs from which the content of the Document can be retrieved."},
     :doi
     {:type "string",
      :pattern "^10.(\\d+)(\\.\\d+)*/[\\w\\-\\.]+",
      :description
      "A `Digital Object Identifier <https://www.doi.org/the-identifier/what-is-a-doi/>`_ for the document."},
     :pmid
     {:type "integer",
      :description
      "A `PubMed unique identifier <https://en.wikipedia.org/wiki/PubMed#PubMed_identifier>`_ for the document."}},
    :source :ga4gh/va-spec},
   :residue
   {:maturity "trial use",
    :description
    "A character representing a specific residue (i.e., molecular species) or groupings of these (\"ambiguity codes\"), using `one-letter IUPAC abbreviations <https://en.wikipedia.org/wiki/International_Union_of_Pure_and_Applied_Chemistry#Amino_acid_and_nucleotide_base_codes>`_ for nucleic acids and amino acids.",
    :type "string",
    :pattern "[A-Z*\\-]",
    :source :ga4gh/vrs},
   :Entity
   {:maturity "trial use",
    :description "Anything that exists, has existed, or will exist.",
    :comment
    "Entity is the root class of the 'gks-core' information model. All core classes that have ids and other general metadata like name, description, type, or extensions descend from this class and inherit its attributes.",
    :heritableProperties
    {:id
     {:type "string",
      :description
      "The 'logical' identifier of the Entity in the system of record, e.g. a UUID.  This 'id' is unique within a given system, but may or may not be globally unique outside the system. It is used within a system to reference an object from another.",
      :comment
      "Note that it is common for implementers to create their own internal logical ids - typically a serially or randomly generated value like a UUID that is assigned to the data object as it is created in a system. But an implementer may choose to re-use an existing, globally unique id from an external system or authority for this purpose (e.g. an HGNC id for a Gene object) - as long as it is unique within the implementing system, and can be used to reference the identified object in this context."},
     :type
     {:type "string",
      :description
      "The name of the class that is instantiated by a data object representing the Entity.",
      :comment
      "MUST be the name of a concrete class from the data model."},
     :name
     {:type "string", :description "A primary name for the entity."},
     :description
     {:type "string",
      :description "A free-text description of the Entity."},
     :aliases
     {:type "array",
      :ordered false,
      :items {:type "string"},
      :description "Alternative name(s) for the Entity."},
     :extensions
     {:type "array",
      :ordered false,
      :items {:ref "#/$defs/Extension"},
      :description
      "A list of extensions to the Entity, that allow for capture of information not directly supported by elements defined in the model.",
      :comment
      "Extension objects have a key-value data structure that allows definition of custom fields in the data itself. Extensions are not expected to be natively understood, but may be used for pre-negotiated exchange of message attributes between systems."}},
    :heritableRequired ["type"],
    :source :ga4gh/gks-core},
   :TraversalBlock
   {:maturity "draft",
    :inherits "gks-core:Entity",
    :type "object",
    :ga4gh {:inherent ["type" "component" "orientation"]},
    :description
    "A component used to describe the orientation of applicable molecular variation within a DerivativeMolecule.",
    :properties
    {:type
     {:extends "type",
      :const "TraversalBlock",
      :default "TraversalBlock",
      :description "MUST be \"TraversalBlock\"."},
     :component
     {:description "The unoriented molecular variation component.",
      :oneOf [{:ref "#/$defs/Adjacency"}]},
     :orientation
     {:type "string",
      :description
      "The orientation of the molecular variation component.",
      :enum ["forward" "reverse_complement"]}},
    :source :ga4gh/vrs},
   :ConceptMapping
   {:type "object",
    :inherits "Element",
    :maturity "trial use",
    :description
    "A mapping to a concept in a terminology or code system.",
    :properties
    {:coding
     {:ref "#/$defs/Coding",
      :description
      "A structured representation of a code for a defined concept in a terminology or code system."},
     :relation
     {:description
      "A mapping relation between concepts as defined by the Simple Knowledge Organization System (SKOS).",
      :type "string",
      :enum
      ["closeMatch"
       "exactMatch"
       "broadMatch"
       "narrowMatch"
       "relatedMatch"]}},
    :required ["relation" "coding"],
    :source :ga4gh/gks-core}})
